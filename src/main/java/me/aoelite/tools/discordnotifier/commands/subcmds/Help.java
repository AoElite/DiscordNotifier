package me.aoelite.tools.discordnotifier.commands.subcmds;

import me.aoelite.tools.discordnotifier.DiscordNotifier;
import me.aoelite.tools.discordnotifier.commands.DiscordNotiferCmd;
import me.aoelite.tools.discordnotifier.commands.SubCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.CommandSender;

public class Help implements SubCommand {

    private DiscordNotiferCmd main;
    public Help(DiscordNotiferCmd main) {
        this.main = main;
    }

    @Override
    public String permission() {
        return "discordnotifier.help";
    }

    @Override
    public String description() {
        return "Lists commands.";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        ComponentBuilder builder = DiscordNotifier.getPrefix()
                .append("Commands: ").color(ChatColor.GRAY)
                .append("\n");

        main.getSubcmds().forEach((s, cmd) -> builder.color(ChatColor.GRAY).append("/").color(ChatColor.AQUA)
                .append(s).append(" " + ChatColor.translateAlternateColorCodes('&', cmd.description())));

        sender.spigot().sendMessage(builder.create());
    }
}
