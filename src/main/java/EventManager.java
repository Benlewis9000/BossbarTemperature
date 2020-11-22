import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EventListener;

public class EventManager implements Listener {

    private final BossbarTemperature plugin;

    /**
     * Manage events for the plugin.
     * @param plugin reference to this plugin.
     */
    public EventManager(BossbarTemperature plugin){

        this.plugin = plugin;
        plugin.getLogger().info("Instantiated EventManager.");

        // Register this class as listener
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){

        // Add player to bossbar
        plugin.getBossbarManager().getBar().addPlayer(e.getPlayer());

    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e){

        // Remove player from bossbar
        plugin.getBossbarManager().getBar().removePlayer(e.getPlayer());

    }

}
