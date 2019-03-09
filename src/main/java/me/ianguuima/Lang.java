package me.ianguuima;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import static me.ianguuima.AutoMessage.getInstance;


public class Lang {

    private static ConfigurationSection msg = getInstance().getConfig().getConfigurationSection("Language");


    public static String INSERTED = msg.getString("inserted");
    public static String NOPERM = msg.getString("noperm");
    public static String UPDATED = msg.getString("updated");
    public static String SHOWING = msg.getString("List.showing");
    public static String SEPARATOR = msg.getString("List.separator-color");


    public static String getConvertedMessage(String msg){
        return msg.replace("&", "§");
    }

    public static void sendMessage(Player p, String message){
        p.sendMessage(message.replace("&", "§"));
    }

    public static void setAndSave(int id, String query){
        getInstance().getConfig().set("Messages." + id, query);
        getInstance().saveConfig();
    }
}
