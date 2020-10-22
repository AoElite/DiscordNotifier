package me.aoelite.tools.discordnotifier.commands.subcmds;

import me.aoelite.tools.discordnotifier.DiscordNotifier;
import me.aoelite.tools.discordnotifier.commands.SubCommand;
import me.aoelite.tools.discordnotifier.utils.SenderUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Raw implements SubCommand {
    @Override
    public String permission() {
        return "discordnotifier.raw";
    }

    @Override
    public String description() {
        return "&b<key> <raw-json> &7Sends json to a channel";
    }

    private final DiscordNotifier notifier;
    public Raw(DiscordNotifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length <= 2) {
            SenderUtil.sendMessage(sender, DiscordNotifier.getPrefix()
                    .append("/discordnotifer raw <key> <json>")
                    .create());
            return;
        }
        String channel = args[1];
        String json = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
        String uuid = sender instanceof Player ? ((Player)sender).getUniqueId().toString() : "null";
        json = json.replaceAll("%sender%", sender.getName());
        json = json.replaceAll("%uuid%", uuid);
        notifier.getMessenger().sendMessageById(channel, json, sender).thenAccept(bol -> Bukkit.getScheduler().runTask(notifier, () -> {
            if (bol) {
                SenderUtil.sendMessage(sender, DiscordNotifier.getPrefix().append("Message was sent successfully.").create());
            } else {
                SenderUtil.sendMessage(sender, DiscordNotifier.getPrefix().append("A error occurred. Check console.").create());
            }
        }));
    }
}
