package me.aoelite.tools.discordnotifier.commands.subcmds;

import me.aoelite.tools.discordnotifier.DiscordNotifier;
import me.aoelite.tools.discordnotifier.commands.SubCommand;
import me.aoelite.tools.discordnotifier.utils.SenderUtil;
import org.bukkit.command.CommandSender;

public class Reload implements SubCommand {
    @Override
    public String permission() {
        return "discordnotifier.reload";
    }

    @Override
    public String description() {
        return "&7Reloads the config.";
    }

    private DiscordNotifier notifier;
    public Reload(DiscordNotifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        notifier.reloadConfig();
        SenderUtil.sendMessage(sender, DiscordNotifier.getPrefix().append("Config has been reloaded.").create());
    }
}
