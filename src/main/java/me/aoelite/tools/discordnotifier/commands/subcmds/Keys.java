package me.aoelite.tools.discordnotifier.commands.subcmds;

import me.aoelite.tools.discordnotifier.DiscordNotifier;
import me.aoelite.tools.discordnotifier.commands.SubCommand;
import me.aoelite.tools.discordnotifier.utils.SenderUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.CommandSender;

import java.util.Enumeration;

public class Keys implements SubCommand {

    private final DiscordNotifier notifier;
    public Keys(DiscordNotifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public String permission() {
        return "discordnotifier.keys";
    }

    @Override
    public String description() {
        return "&7List keys that are in the config";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
       ComponentBuilder cb = DiscordNotifier.getPrefix()
                .append("Listing keys: \n").color(ChatColor.GRAY);
      Enumeration<String> keys = notifier.getMessenger().getWebhooks().keys();
      while (keys.hasMoreElements()) {
          cb.append(" -> ").color(ChatColor.DARK_GRAY);
          cb.append(keys.nextElement()).color(ChatColor.AQUA);
      }
      SenderUtil.sendMessage(sender, cb.create());
    }
}
