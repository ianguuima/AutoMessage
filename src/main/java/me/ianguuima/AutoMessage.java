package me.ianguuima;

import me.ianguuima.commands.AutoMessageCommand;
import me.ianguuima.entity.message.MessageManager;
import me.ianguuima.entity.plugin.PluginManager;
import me.ianguuima.entity.plugin.SendingType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class AutoMessage extends JavaPlugin {

    public static boolean STATUS = true;
    private static int count = 0;

    private static AutoMessage instance;
    private static PluginManager pluginManager;

    private static MessageManager messageManager;
    private static Random r;

    @Override
    public void onEnable() {
        instance = this;
        configuration();
        pluginManager = new PluginManager();

        if (!Bukkit.getPluginManager().isPluginEnabled(this)) return;

        r = new Random();
        messageManager = new MessageManager();
        registerCommands();
        tasker();

    }


    private void registerCommands() {
        getCommand("automessage").setExecutor(new AutoMessageCommand());
    }

    private void configuration() {
        getConfig().options().copyDefaults(false);
        saveDefaultConfig();
    }


    public static MessageManager getMessageManager() {
        return messageManager;
    }

    public static AutoMessage getInstance() {
        return instance;
    }

    public static PluginManager getPluginManager() {
        return pluginManager;
    }

    public static Random getRandom() {
        return r;
    }

    private void tasker() {


        Bukkit.getScheduler().scheduleAsyncRepeatingTask(getInstance(), new BukkitRunnable() {
            @Override
            public void run() {
                if (!STATUS) return;
                List<String> lista = getMessageManager().sendingMessagesOrder();

                if (getPluginManager().getPlugin().getType() == SendingType.RANDOM){
                    int valor = getRandom().nextInt(lista.size());
                    Bukkit.broadcastMessage(lista.get(valor).replace("&", "§"));
                    return;
                }

                Bukkit.broadcastMessage(lista.get(count).replace("&", "§"));
                count++;
                if (count >= lista.size()) count = 0;



            }
        }, 0, getPluginManager().getPlugin().getInterval() * 20);

    }


}