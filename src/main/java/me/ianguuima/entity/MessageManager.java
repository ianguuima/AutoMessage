package me.ianguuima.entity;

import me.ianguuima.Lang;
import me.ianguuima.dao.Dao;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashSet;

import static me.ianguuima.AutoMessage.getInstance;
import static me.ianguuima.AutoMessage.getRandom;

public class MessageManager implements Dao<Message> {

    private HashSet<Message> messages;

    public MessageManager() {
        messages = new HashSet<>();
        loadConfig();
    }

    public Message getMessage(int id) {
        return messages.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void insert(String message) {
        int valor = getRandom().nextInt(1000);
        while (getMessage(valor) != null) getRandom().nextInt(1000);
        messages.add(new Message(valor, message));
        Lang.setAndSave(valor, message);

    }

    public void update(String newmessage, int id) {
        delete(getMessage(id));
        messages.add(new Message(id, newmessage.replace("&", "§")));
        Lang.setAndSave(id, newmessage);
    }

    public void delete(Message m) {
        messages.remove(m);
        Lang.setAndSave(m.getId(), null);
    }


    private void loadConfig(){
        ConfigurationSection c = getInstance().getConfig().getConfigurationSection("Messages");
        if (c == null) return;
        for (String s : c.getKeys(false)){
            String message = getInstance().getConfig().getString("Messages." + s).replace("&", "§");
            messages.add(new Message(Integer.valueOf(s), message));
        }
    }

    public HashSet<Message> getMessages() {
        return messages;
    }
}
