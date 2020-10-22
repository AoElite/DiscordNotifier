package me.aoelite.tools.discordnotifier.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.concurrent.ConcurrentHashMap;

public class DiscordData {

    private ConcurrentHashMap<String, String> webHooks = new ConcurrentHashMap<>();

    private final JsonObject notifyJson = new JsonParser().parse("{\"embeds\":[{\"title\": \"Discord Notification from %sender%\",\"description\": \"%message%\"}]}").getAsJsonObject();

    public String getNotifyJson() {
        return notifyJson.toString();
    }

    public ConcurrentHashMap<String, String> getWebHooks() {
        return webHooks;
    }

    private int configVersion = 3;

    public int getConfigVersion() {
        return configVersion;
    }

    public DiscordData setWebHooks(ConcurrentHashMap<String, String> webHooks) {
        this.webHooks = webHooks;
        return this;
    }

    public DiscordData setConfigVersion(int configVersion) {
        this.configVersion = configVersion;
        return this;
    }

}
