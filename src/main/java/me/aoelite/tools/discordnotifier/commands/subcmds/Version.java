package me.aoelite.tools.discordnotifier.commands.subcmds;

import me.aoelite.tools.discordnotifier.DiscordNotifier;
import me.aoelite.tools.discordnotifier.commands.SubCommand;
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

    private DiscordNotifier notifier;
    public Version(DiscordNotifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        notifier.getMessenger().getExecutor().execute(() -> {
            try {
                final double latest = Double.parseDouble(getPasteBin().get(0));
                sender.spigot().sendMessage(DiscordNotifier.getPrefix().append("Checking version...").create());
                notifier.getServer().getScheduler().runTask(notifier, () -> {
                    if (DiscordNotifier.getVersion() >= latest) {
                        sender.spigot().sendMessage(DiscordNotifier.getPrefix().append("Plugin is updated.")
                               .create());
                    } else {
                        sender.spigot().sendMessage(DiscordNotifier.getPrefix().append("Plugin is outdated.")
                                .create());
                    }
                });
            } catch (Exception e) {
                sender.spigot().sendMessage(DiscordNotifier.getPrefix().append("A error occurred while trying to check versions.").create());
                Bukkit.getServer().getConsoleSender().spigot().sendMessage(DiscordNotifier.getPrefix()
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
