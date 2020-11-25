import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {

    // Members
    private final BossbarTemperature plugin;
    private final FileConfiguration config;

    // Getters
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Manage config files for the plugin.
     * @param plugin reference to this plugin.
     */
    public ConfigManager(BossbarTemperature plugin){

        this.plugin = plugin;
        plugin.getLogger().info("Instantiated ConfigManager.");

        // Save default if no existing config found
        this.plugin.saveDefaultConfig();

        config = this.plugin.getConfig();

    }

    /**
     * Reload configuration file.
     */
    public void reloadConfig(){

        plugin.reloadConfig();
        // Update bossbar to new config and re-run
        plugin.getBossbarManager().updateSettings();
        plugin.getBossbarManager().run();

    }

}
