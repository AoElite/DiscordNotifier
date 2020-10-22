package me.aoelite.tools.discordnotifier;

import me.aoelite.tools.discordnotifier.commands.DiscordNotiferCmd;
import me.aoelite.tools.discordnotifier.config.DiscordData;
import me.aoelite.tools.discordnotifier.config.JsonConfig;
import me.aoelite.tools.discordnotifier.utils.SenderUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DiscordNotifier extends JavaPlugin {

    private DiscordMessenger messenger;
    private JsonConfig jsonConfig;
    private DiscordData discordData = new DiscordData();
    private final static double version = 1.05;
    private static DiscordNotifier instance = null;

    public DiscordData getDiscordData() {
        return discordData;
    }

    public static ComponentBuilder getPrefix() {
        return new ComponentBuilder("")
                .append("[").color(ChatColor.DARK_GRAY)
                .append("DiscordNotifier").color(ChatColor.AQUA)
                .append("]").color(ChatColor.DARK_GRAY)
                .append(" ").color(ChatColor.GRAY);
    }

    public static double getVersion() {
        return version;
    }

    public DiscordMessenger getMessenger() {
        return messenger;
    }

    @Override
    public void onEnable() {
        instance = this;
        jsonConfig = new JsonConfig(this);
        messenger = new DiscordMessenger(discordData);
        reloadConfig();
        new DiscordNotiferCmd(this);


        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            //TODO: Implement PlaceHolderAPI
        }

        SenderUtil.sendMessage(Bukkit.getConsoleSender(), getPrefix()
                .append("Discord Notifier has successfully loaded.").color(ChatColor.GRAY)
                .append(" (Created by ").color(ChatColor.GRAY)
                .append("AoElite").color(ChatColor.AQUA)
                .append(")").color(ChatColor.GRAY)
                .create());

    }

    public static DiscordNotifier getInstance() {
        return instance;
    }

    public void reloadConfig() {
        if (jsonConfig.exists("config.json")) {
            discordData = jsonConfig.getObject("config.json", DiscordData.class);
        } else {
            discordData = new DiscordData();
          /*  JsonObject notifyJson = new JsonParser().parse("{\"embeds\":[{\"title\": \"Discord Notification from %sender%\",\"description\": \"%message%\"}]}").getAsJsonObject();
            discordData.getNotifyMap().put("default", notifyJson);*/
            discordData.getWebHooks().put("key", "webhook url");
            jsonConfig.write("config.json", discordData);
        }
        messenger.reload(discordData);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }

}
