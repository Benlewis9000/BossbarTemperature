import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.EventListener;

public class BossbarManager {

    private final BossbarTemperature plugin;

    // Bar traits
    private BossBar bar;
    private int currentTemp;

    // Config settings
    private String layout;
    private int minTemp;
    private int maxTemp;
    private int peakTime;
    private long tickrate;
    private String worldName;

    public BossBar getBar() {
        return bar;
    }

    /**
     * Manage the boss bar for the plugin.
     * @param plugin reference to this plugin.
     */
    public BossbarManager(final BossbarTemperature plugin){

        this.plugin = plugin;

        plugin.getLogger().info("Instantiated BossbarManager.");

        // load in configuration variables - todo: not very reload friendly, abstract to method?
        layout = this.plugin.getConfig().getString("layout");
        minTemp = this.plugin.getConfig().getInt("min-temperature");
        maxTemp = this.plugin.getConfig().getInt("max-temperature");
        peakTime = this.plugin.getConfig().getInt("peak-time");
        tickrate = this.plugin.getConfig().getLong("tickrate");
        worldName = this.plugin.getConfig().getString("world-name");

        bar = Bukkit.createBossBar("Loading...", BarColor.BLUE, BarStyle.SOLID);

        // Schedule repeating updates
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                updateTitle();
            }
        }, 0L, tickrate);

        // Add all online player to bar
        for (Player p : Bukkit.getServer().getOnlinePlayers()) bar.addPlayer(p);

        bar.setVisible(true);

    }

    /**
     * Disable the bar by removing all players.
     */
    public void disable(){

        this.bar.removeAll();

    }

    /**
     * Update bossbar settings to those defined by the config file.
     */
    public void updateSettings(){

        // load in configuration variables
        layout = this.plugin.getConfig().getString("layout");
        minTemp = this.plugin.getConfig().getInt("min-temperature");
        maxTemp = this.plugin.getConfig().getInt("max-temperature");
        peakTime = this.plugin.getConfig().getInt("peak-time");
        tickrate = this.plugin.getConfig().getLong("tickrate");
        worldName = this.plugin.getConfig().getString("world-name");

    }

    /**
     * Update the title with new temperatures and/or layout.
     */
    private void updateTitle(){

        // Calculate temperature
        currentTemp = calculateTemp();

        // Prepare bar title from config layout, substituting placeholders
        String barTitle = layout;
        barTitle = barTitle.replaceAll("%celsius%", String.valueOf(currentTemp));
        barTitle = barTitle.replaceAll("%fahrenheit%", String.valueOf(Math.round(celsiusToFahrenheit(currentTemp))));

        bar.setTitle(ChatColor.translateAlternateColorCodes('&', barTitle));

    }

    /**
     * Calculate current temperature based on configured settings.
     * @return current temperature (celsius)
     */
    private int calculateTemp(){

        int time = (int) Bukkit.getServer().getWorld(worldName).getTime();

        // Calculate temp (y value) on a manipulated sine wave
        return (int) Math.round(
                Math.sin( (time * 0.000262) - ( (peakTime/24000.0) * Math.PI * 2 ) + (0.5 * Math.PI) ) * ((maxTemp-minTemp)/2.0)
                            + (minTemp + ((maxTemp-minTemp)/2.0))
        );
    }

    /**
     * Convert a temperature from celsius to fahrenheit.
     * @param c temperature in celsius.
     * @return temperature in fahrenheit.
     */
    private static double celsiusToFahrenheit(double c){

        return ((c*9)/5)+52;

    }

}
