import org.bukkit.entity.Boss;
import org.bukkit.plugin.java.JavaPlugin;

public class BossbarTemperature extends JavaPlugin {

    // Members
    private ConfigManager configManager;
    private BossbarManager bossbarManager;

    // Getters
    public ConfigManager getConfigManager() {
        return configManager;
    }
    public BossbarManager getBossbarManager() {
        return bossbarManager;
    }

    @Override
    public void onEnable(){

        // Initialise managers
        configManager = new ConfigManager(this);
        bossbarManager = new BossbarManager(this);

        // Register event manager
        new EventManager(this);

        // Register command manager
        this.getCommand("temp").setExecutor(new CommandManager(this));

    }

    @Override
    public void onDisable(){

        // Disable bar
        bossbarManager.disable();
        // Save FileConfiguration to file
        this.saveConfig();

    }

}
