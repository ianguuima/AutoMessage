package me.ianguuima;

import me.ianguuima.commands.AutoMessageCommand;
import me.ianguuima.entity.Message;
import me.ianguuima.entity.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AutoMessage extends JavaPlugin {

    public static boolean STATUS = true;

    private static AutoMessage instance;
    private static MessageManager messageManager;
    private static Random r;


    //TODO:: Usar lombok no objeto


    @Override
    public void onEnable() {
        instance = this;
        r = new Random();
        configuration();

        messageManager = new MessageManager();
        registerCommands();
        tasker();
    }


    private void registerCommands(){
        getCommand("automessage").setExecutor(new AutoMessageCommand());
    }

    private void configuration(){
        getConfig().options().copyDefaults(false);
        saveDefaultConfig();
    }


    public static MessageManager getMessageManager() {
        return messageManager;
    }

    public static AutoMessage getInstance() {
        return instance;
    }

    public static Random getRandom() {
        return r;
    }

    private void tasker(){
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(getInstance(), () -> {
            if (!STATUS) return;
            List<String> lista = getMessageManager().getMessages().stream().map(Message::getMessage).collect(Collectors.toList());
            int valor = getRandom().nextInt(lista.size());
            Bukkit.broadcastMessage(lista.get(valor));
        }, 0, 20 * Lang.DELAY);

    }


}