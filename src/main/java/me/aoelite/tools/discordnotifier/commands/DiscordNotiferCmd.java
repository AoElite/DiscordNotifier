package me.aoelite.tools.discordnotifier.commands;

import me.aoelite.tools.discordnotifier.DiscordNotifier;
import me.aoelite.tools.discordnotifier.commands.subcmds.*;
import me.aoelite.tools.discordnotifier.utils.SenderUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DiscordNotiferCmd implements CommandExecutor, TabCompleter {

    private final HashMap<String, SubCommand> subcmds = new HashMap<>();

    public HashMap<String, SubCommand> getSubcmds() {
        return subcmds;
    }


    private final DiscordNotifier notifier;

    public DiscordNotiferCmd(DiscordNotifier plugin) {
        this.notifier = plugin;
        plugin.getCommand("discordnotifier").setExecutor(this);
        plugin.getCommand("dn").setExecutor(this);
        subcmds.put("help", new Help(this));
        subcmds.put("reload", new Reload(plugin));
        subcmds.put("version", new Version(plugin));
        subcmds.put("notify", new Notify(plugin));
        subcmds.put("raw", new Raw(plugin));
        subcmds.put("keys", new Keys(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length <= 0) {
            subcmds.get("help").onCommand(sender, args);
            return true;
        }
        if (subcmds.containsKey(args[0].toLowerCase())) {
          SubCommand sub = subcmds.get(args[0]);
          if (sender.hasPermission(sub.permission())) {
              sub.onCommand(sender, args);
          } else {
              SenderUtil.sendMessage(sender, DiscordNotifier.getPrefix().append("Invalid permissions.").create());
          }
        } else {
            SenderUtil.sendMessage(sender, DiscordNotifier.getPrefix().append("Invalid command.").create());
        }
        return true;
    }

    private List<String> startsWith(List<String> list, String word) {
        List<String> avalaible = new ArrayList<>();
        for (String s : list) {
            if (s.startsWith(word)) avalaible.add(s);
        }
        return avalaible;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length <= 0) return null;
        List<String> cmds = new ArrayList<>(subcmds.keySet());
        if (args.length <= 1) {
            return startsWith(cmds, args[0]);
        }
        if (args.length == 2 && (args[0].equalsIgnoreCase("notify") || args[0].equalsIgnoreCase("raw"))) {
            List<String> list = new ArrayList<>(notifier.getDiscordData().getWebHooks().keySet());
            return startsWith(list, args[1]);
        }
        return null;
    }
}
