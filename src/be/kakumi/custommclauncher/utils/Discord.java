package be.kakumi.custommclauncher.utils;

import be.kakumi.custommclauncher.Config;
import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class Discord {

    private DiscordRPC discord = DiscordRPC.INSTANCE;
    DiscordRichPresence presence;

    public Discord() {
        //Discord
        String steamId = "";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("Discord is Ready!");
        discord.Discord_Initialize(Config.DISCORD_APP_ID, handlers, true, steamId);
        presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000; // epoch second
        presence.details = "Launcher";
        presence.state = Config.DISCORD_LINK;
        presence.largeImageKey = Config.DISCORD_LARGE_IMG_KEY;
        presence.largeImageText = Config.LAUNCHER_NAME;
        discord.Discord_UpdatePresence(presence);
        // in a worker thread
        new Thread(() -> {
            System.out.println(presence.state);
            while (!Thread.currentThread().isInterrupted()) {
                discord.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                    discord.Discord_Shutdown();
                }
            }
        }, "RPC-Callback-Handler").start();
        //Fin discord
    }

    public void setDiscordState(String state) {
        presence.state = state;
        discord.Discord_UpdatePresence(presence);
    }

    public void setDiscordDetails(String details) {
        presence.details = details;
        discord.Discord_UpdatePresence(presence);
    }
}
