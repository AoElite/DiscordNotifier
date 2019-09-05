package me.aoelite.tools.discordnotifier.commands.subcmds;

import me.aoelite.tools.discordnotifier.DiscordNotifier;
import me.aoelite.tools.discordnotifier.commands.DiscordNotiferCmd;
import me.aoelite.tools.discordnotifier.commands.SubCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
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
        return "&7Lists commands.";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        ComponentBuilder builder = DiscordNotifier.getPrefix()
                .append("Commands: ").color(ChatColor.GRAY);

        main.getSubcmds().forEach((s, cmd) -> builder.append("\n").color(ChatColor.AQUA).append("/dn ")
                .append(s).append(" " + ChatColor.translateAlternateColorCodes('&', cmd.description())));

       sender.sendMessage(TextComponent.toLegacyText(builder.create()));
    }
}
