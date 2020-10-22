package me.aoelite.tools.discordnotifier.commands.subcmds;

import me.aoelite.tools.discordnotifier.DiscordNotifier;
import me.aoelite.tools.discordnotifier.commands.SubCommand;
import me.aoelite.tools.discordnotifier.utils.SenderUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Version implements SubCommand {

    //https://pastebin.com/raw/W0Ak57KE

    @Override
    public String permission() {
        return "discordnotifier.version";
    }

    @Override
    public String description() {
        return "&7Checks if the plugin is updated.";
    }

    private final DiscordNotifier notifier;
    public Version(DiscordNotifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Bukkit.getScheduler().runTaskAsynchronously(notifier, () -> {
            try {
                final double latest = Double.parseDouble(getPasteBin().get(0));
                SenderUtil.sendMessage(sender, DiscordNotifier.getPrefix().append("Checking version...").create());
                notifier.getServer().getScheduler().runTask(notifier, () -> {
                    if (DiscordNotifier.getVersion() >= latest) {
                        SenderUtil.sendMessage(sender, DiscordNotifier.getPrefix().append("Plugin is up to date.")
                                .create());
                    } else {
                        SenderUtil.sendMessage(sender, DiscordNotifier.getPrefix().append("Plugin is outdated.")
                                .create());
                    }
                });
            } catch (Exception e) {
                SenderUtil.sendMessage(sender, DiscordNotifier.getPrefix().append("A error occurred while trying to check versions.").create());
                SenderUtil.sendMessage(Bukkit.getConsoleSender(), DiscordNotifier.getPrefix()
                        .append(e.getMessage())
                        .create());
            }
        });
    }

    private List<String> getPasteBin() {
        List<String> list = new ArrayList<>();
        try {
            URL uri = new URL("https://pastebin.com/raw/W0Ak57KE");
            Scanner s = new Scanner(uri.openStream());
            while (s.hasNextLine()) {
                list.add(s.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
