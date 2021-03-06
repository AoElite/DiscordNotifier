package me.aoelite.tools.discordnotifier;

import me.aoelite.tools.discordnotifier.config.DiscordData;
import me.aoelite.tools.discordnotifier.utils.SenderUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.*;

public class DiscordMessenger {

    private final Executor executor;
    private ConcurrentHashMap<String, String> webhooks;

    public ConcurrentHashMap<String, String> getWebhooks() {
        return webhooks;
    }

    public DiscordMessenger(DiscordData data) {
        this.executor = runnable -> Bukkit.getScheduler().runTaskAsynchronously(DiscordNotifier.getInstance(), runnable);
        webhooks = data.getWebHooks();
        reload(data);
    }

    //private final Pattern pattern = Pattern.compile("discordapp\\.com/api/webhooks/(\\d+)/");

    public void reload(DiscordData data) {
        webhooks = data.getWebHooks();
   /*     channelKeys.clear();
        webhooks.forEach((k, v) -> {
            Matcher m = pattern.matcher(k);
            if (m.find()) {
                String id = m.group(1);
                channelKeys.put(id, k);
            }
        });*/
    }

    public CompletableFuture<Boolean> sendMessageByWebhook(String webhook, String json) {
        return CompletableFuture.supplyAsync(() -> {
            Future<Boolean> future = send(webhook, "DiscordNotifier", json);
            try {
                return future.get(30, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {

                SenderUtil.sendMessage(Bukkit.getConsoleSender(), DiscordNotifier.getPrefix()
                        .append(e.getMessage())
                        .create());

                return false;
            }
        }, executor);
    }

    public CompletableFuture<Boolean> sendMessageById(String id, String json, CommandSender sender) {
        if (!webhooks.containsKey(id)) {
            SenderUtil.sendMessage(sender, DiscordNotifier.getPrefix()
                    .append("The key is not registered. Check your config & reload it.").create());
            return CompletableFuture.completedFuture(false);
        }
        return sendMessageById(id, json);
    }

    public CompletableFuture<Boolean> sendMessageById(String id, String json) {
        if (!webhooks.containsKey(id)) {
            return CompletableFuture.completedFuture(false);
        }
        return sendMessageByWebhook(webhooks.get(id), json);
    }


    private Future<Boolean> send(final String webhook_url, final String user_agent, String json) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL(webhook_url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("User-Agent", user_agent);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(json);
                wr.flush();
                wr.close();
                final int responseCode = connection.getResponseCode();
                if (!(responseCode == 200 || responseCode == 201 || responseCode == 204)) {
                    SenderUtil.sendMessage(Bukkit.getConsoleSender(), DiscordNotifier.getPrefix()
                            .append("Message was not sent. Response code: " + responseCode)
                            .create());
                    return false;
                }
            } catch (IOException e) {
                SenderUtil.sendMessage(Bukkit.getConsoleSender(), DiscordNotifier.getPrefix()
                        .append(e.getMessage())
                        .create());
                return false;
            }
            return true;
        }, executor);
    }

}
