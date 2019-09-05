package me.aoelite.tools.discordnotifier.commands;

import me.aoelite.tools.discordnotifier.DiscordNotifier;
import me.aoelite.tools.discordnotifier.commands.subcmds.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DiscordNotiferCmd implements CommandExecutor, TabCompleter {

    private HashMap<String, SubCommand> subcmds = new HashMap<>();

    public HashMap<String, SubCommand> getSubcmds() {
        return subcmds;
    }


    private DiscordNotifier notifier;

    public DiscordNotiferCmd(DiscordNotifier plugin) {
        this.notifier = plugin;
        plugin.getCommand("discordnotifier").setExecutor(this);
        plugin.getCommand("dn").setExecutor(this);
        subcmds.put("help", new Help(this));
        subcmds.put("reload", new Reload(plugin));
        subcmds.put("version", new Version(plugin));
        subcmds.put("notify", new Notify(plugin));
        subcmds.put("raw", new Raw(plugin));
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
              sender.spigot().sendMessage(DiscordNotifier.getPrefix().append("Invalid permissions.").create());
          }
        } else {
            sender.spigot().sendMessage(DiscordNotifier.getPrefix().append("Invalid command.").create());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length <= 0) return null;
        if (args[0].equalsIgnoreCase("")) {
            return new ArrayList<>(subcmds.keySet());
        }
        if (args.length >= 2 && args.length <= 2 && (args[0].equalsIgnoreCase("notify") || args[0].equalsIgnoreCase("raw"))) {
            return new ArrayList<>(notifier.getDiscordData().getWebHooks().keySet());
        }
        return null;
    }
}
