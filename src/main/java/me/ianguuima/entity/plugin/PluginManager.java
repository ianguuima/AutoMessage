package me.ianguuima.entity.plugin;

import me.ianguuima.exceptions.SendingFormatException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import static me.ianguuima.AutoMessage.getInstance;

public class PluginManager {

    private Plugin plugin;
    private static ConfigurationSection config = getInstance().getConfig().getConfigurationSection("Configuration");

    public PluginManager() {
        try {
            loadConfiguration();
        } catch (SendingFormatException e) {
            Bukkit.getConsoleSender().sendMessage("§cAutoMessage ERROR>> §7" + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(getInstance());
        }
    }


    private void loadConfiguration() throws SendingFormatException {
        int delay = config.getInt("interval");
        String type = config.getString("sending");
        if (!(type.equalsIgnoreCase("RANDOM") || type.equalsIgnoreCase("ORDER"))) throw new SendingFormatException("The field SENDING is WRONG! It must be §aRANDOM§7 or §aORDER§7, take a look!");
        plugin = new Plugin(delay, SendingType.valueOf(type));
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
