package me.ianguuima.entity.message;

import me.ianguuima.Lang;
import me.ianguuima.dao.Dao;
import me.ianguuima.entity.plugin.SendingType;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static me.ianguuima.AutoMessage.*;

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


    public List<String> sendingMessagesOrder(){
        SendingType type = getPluginManager().getPlugin().getType();


        if (type == SendingType.RANDOM){
            return getMessages().stream().map(Message::getMessage).collect(Collectors.toList());
        }

        if (type == SendingType.ORDER){
            return getMessages().stream().sorted(Comparator.comparingInt(Message::getId)).map(Message::getMessage).collect(Collectors.toList());
        }


        return null;


    }


    public HashSet<Message> getMessages() {
        return messages;
    }
}
