package me.aoelite.tools.discordnotifier.commands.subcmds;

import me.aoelite.tools.discordnotifier.DiscordNotifier;
import me.aoelite.tools.discordnotifier.commands.SubCommand;
import me.aoelite.tools.discordnotifier.utils.SenderUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Notify implements SubCommand {

    @Override
    public String permission() {
        return "discordnotifier.notify";
    }

    @Override
    public String description() {
        return "&b<channel id> <message> &7Sends a message to a channel.";
    }

    private DiscordNotifier notifier;
    public Notify(DiscordNotifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length <= 2) {
            SenderUtil.sendMessage(sender, DiscordNotifier.getPrefix()
                    .append("/discordnotifer notify <channelid> <message>")
                    .create());
            return;
        }
        String channel = args[1];
        String message = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
        String json = notifier.getDiscordData().getNotifyJson();
        String uuid = sender instanceof Player ? ((Player)sender).getUniqueId().toString() : "null";
        json = json.replaceAll("%message%", message);
        json = json.replaceAll("%sender%", sender.getName());
        json = json.replaceAll("%uuid%", uuid);
        notifier.getMessenger().sendMessageById(channel, json, sender).thenAccept(bol -> Bukkit.getScheduler().runTask(notifier, () -> {
            if (bol) {
                SenderUtil.sendMessage(sender, DiscordNotifier.getPrefix().append("Message was sent successfully.").create());
            } else {
                SenderUtil.sendMessage(sender, DiscordNotifier.getPrefix().append("Message was sent successfully.").create());
            }
        }));
    }
}
