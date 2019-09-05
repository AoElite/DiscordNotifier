package me.aoelite.tools.discordnotifier.commands;

import org.bukkit.command.CommandSender;

public interface SubCommand {

    String permission();

    String description();

    void onCommand(CommandSender sender, String[] args);

}
