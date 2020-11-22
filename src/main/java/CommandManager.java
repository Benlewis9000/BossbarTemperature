import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor {

    // Members
    private final BossbarTemperature plugin;

    public CommandManager(BossbarTemperature plugin){

        this.plugin = plugin;

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        // Ensure args have been included
        if (args.length < 1) return incorrectUsage(sender);
        // Execute appropriate subcommand
        else if (args[0].equalsIgnoreCase("reload")) return commandReload(sender, args);
        else if (args[0].equalsIgnoreCase("set")) return commandSetTemp(sender, args);
        else if (args[0].equalsIgnoreCase("peaktime")) return commandSetPeak(sender, args);
        else return incorrectUsage(sender);

    }

    /**
     * Reload the configuration from file.
     * @param sender of command
     * @param args arguments
     * @return true
     */
    public boolean commandReload(CommandSender sender, String[] args){

        plugin.getConfigManager().reloadConfig();

        sender.sendMessage(ChatColor.GREEN + "BossbarTemperature has been reloaded.");

        return true;
    }

    /**
     * Set the new min/max temperature.
     * @param sender of command
     * @param args arguments
     * @return true
     */
    public boolean commandSetTemp(CommandSender sender, String[] args){

        // Ensure minimal number of args
        if (args.length < 3) return incorrectUsage(sender);
        else try {

            // Parse int value
            int temp = Integer.parseInt(args[2]);

            // Establish setting max or min
            if (args[1].equalsIgnoreCase("min")) {

                // Set new config value, and update bossbar
                plugin.getConfigManager().getConfig().set("min-temperature", temp);
                plugin.getBossbarManager().updateSettings();
                return true;

            }
            else if (args[1].equalsIgnoreCase("max")) {

                plugin.getConfigManager().getConfig().set("max-temperature", temp);
                plugin.getBossbarManager().updateSettings();
                return true;

            }
            else return incorrectUsage(sender);

        }
        // Caught if invalid int passed
        catch (NumberFormatException e){

            return incorrectUsage(sender);

        }

    }

    /**
     * Reload the new peak time.
     * @param sender of command
     * @param args arguments
     * @return true
     */
    public boolean commandSetPeak(CommandSender sender, String[] args){

        // Ensure minimal number of args
        if (args.length < 2) return incorrectUsage(sender);
        else try {

            // Parse int value
            int peak = Integer.parseInt(args[1]);

            // Set new config value, and update bossbar
            plugin.getConfigManager().getConfig().set("peak-time", peak);
            plugin.getBossbarManager().updateSettings();
            return true;

        }
        // Caught if invalid int passed
        catch (NumberFormatException e){

            return incorrectUsage(sender);

        }

    }

    /**
     * Send correct usage message to sender.
     * @param sender of command
     * @return true
     */
    public boolean incorrectUsage(CommandSender sender){

        sender.sendMessage("Incorrect usage.\n" +
                " - /temp reload\n" +
                " - /temp set max <celsius>\n" +
                " - /temp set min <celsius>\n" +
                " - /temp peaktime <tick>\n");

        return true;
    }

}
