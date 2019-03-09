package me.ianguuima.commands;

import me.ianguuima.Lang;
import me.ianguuima.entity.Message;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static me.ianguuima.AutoMessage.STATUS;
import static me.ianguuima.AutoMessage.getMessageManager;

public class AutoMessageCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
        if (!(s instanceof Player)) return false;
        Player p = (Player) s;
        if (c.getName().equalsIgnoreCase("automessage")) {

            if (args.length == 0) {
                p.sendMessage("§c/automessage §8- §7The main command of the plugin");
                p.sendMessage("§c/automessage status §8- §7Enable or disable the sending of messages");
                p.sendMessage("§c/automessage add <message> §8- §7Add a new automatic message");
                p.sendMessage("§c/automessage remove <id> §8- §7Remove a existent message");
                p.sendMessage("§c/automessage edit <id> <new-message> §8- §7Edit a existent message");
                p.sendMessage(" ");
                p.sendMessage("§c/automessage list §8- §7List of messages");
            }


            if (args.length == 1) {

                switch (args[0]) {
                    case "list":
                        ComponentBuilder cb = new ComponentBuilder(Lang.getConvertedMessage(Lang.SHOWING));
                        String SEPARATOR = "";

                        for (Message v : getMessageManager().getMessages()) {

                            TextComponent message = new TextComponent(Lang.getConvertedMessage(Lang.SEPARATOR) + v.getId());
                            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aMessage: §7"+ v.getMessage() + "\n§aHow to edit: §7/automessage edit " + v.getId() + " <new-message>").create()));
                            message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/automessage edit "  + v.getId() +  " <new-message>"));
                            cb.append(SEPARATOR);
                            cb.append(message);
                            SEPARATOR = ", ";
                        }

                        p.spigot().sendMessage(cb.create());
                        break;

                    case "status":
                        if (STATUS) {
                            STATUS = false;
                            p.sendMessage("§eINFO §8>> §7Automatic messages turned §coff§7!");
                        } else {
                            STATUS = true;
                            p.sendMessage("§eINFO §8>> §7Automatic messages turned §aon§7!");
                        }

                        break;
                }

            }


            if (args.length >= 2) {

                int valor = 0;
                List<String> lista;

                if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("edit")){
                    try{
                        valor = Integer.parseInt(args[1]);

                    } catch (NumberFormatException e){
                        p.sendMessage("§cERROR §8>> §7Only numbers are accepted!");
                        return false;
                    }

                    if (getMessageManager().getMessage(valor) == null){
                        p.sendMessage("§cERROR §8>> §7Wait... this value doesn't exists! :(");
                        return false;
                    }
                }

                switch (args[0]) {


                    case "edit":
                        lista = Arrays.stream(Arrays.copyOfRange(args, 2, args.length)).collect(Collectors.toList());
                        getMessageManager().update(String.join(" ", lista), valor);
                        p.sendMessage(Lang.getConvertedMessage(Lang.UPDATED));
                        break;


                    case "add":
                        lista = Arrays.stream(Arrays.copyOfRange(args, 1, args.length)).collect(Collectors.toList());
                        getMessageManager().insert(String.join(" ", lista));
                        Lang.sendMessage(p, Lang.INSERTED);
                        break;


                    case "remove":
                        getMessageManager().delete(getMessageManager().getMessage(valor));
                        p.sendMessage("§aSUCCESS §8>> §7The message has been removed!");
                        break;


                    default:
                        p.sendMessage("§7Invalid command/argument, try again!");
                        break;
                }
            }


        }
        return false;
    }


}
