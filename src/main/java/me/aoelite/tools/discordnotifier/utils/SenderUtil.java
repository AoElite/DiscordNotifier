package me.aoelite.tools.discordnotifier.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;

public class SenderUtil {

    public static void sendMessage(CommandSender sender, BaseComponent[] bc) {
        sender.sendMessage(TextComponent.toLegacyText(bc));
    }

}
