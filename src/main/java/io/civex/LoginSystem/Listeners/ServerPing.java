package io.civex.LoginSystem.Listeners;

import io.civex.LoginSystem.LoginQueue;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.UUID;

public class ServerPing implements Listener {

    private LoginQueue plugin;

    public ServerPing(LoginQueue plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        UUID uuid = plugin.getUUIDFromIP(event.getAddress().getHostAddress());

        if (uuid != null && plugin.getPositionInQueue(uuid) > 0) {
            if (plugin.isOnTheClock(uuid)) {
                event.setMotd("YOU'RE ON THE CLOCK, GO GO GO GO!");
            } else {
                event.setMotd(plugin.getNameFromUUID(uuid) + " is [#" + plugin.getPositionInQueue(uuid) + "] in the queue\nApproximate wait: " + plugin.getAverageTime());
            }
        }
    }
}
