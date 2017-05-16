package io.civex.LoginSystem.Listeners;

import io.civex.LoginSystem.LoginSystem;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

/**
 * Created by Ryan on 5/15/2017.
 */
public class PreLogin implements Listener
{
    LoginSystem plugin;

    public PreLogin(LoginSystem pl)
    {
        this.plugin = pl;
    }


    //if slots available >= your queue pos allow in.
    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event)
    {
        int playerCount = plugin.getServer().getOnlinePlayers().size();
        int maxPlayerCount = plugin.getServer().getMaxPlayers();
        int availableSlots = maxPlayerCount - playerCount;
        int queuePos = plugin.getPositionInQueue(event.getUniqueId());
        int highestQueuePos = plugin.getHighestQueuePos();

        if (playerCount >= maxPlayerCount)
        {
            if (queuePos > 0)
            {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, "You're [#" + queuePos + "] in the queue.");
            }
            else
            {
                plugin.addUserToLoginQueue(event.getUniqueId());
                queuePos = plugin.getPositionInQueue(event.getUniqueId());
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, "You're [#" + queuePos + "] in the queue.");
            }
        }
        else if (availableSlots > 0)
        {
            if (queuePos > 0)
            {
                if (queuePos >= availableSlots)
                {
                    event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, "You're [#" + queuePos + "] in the queue.");
                }
            }
            else
            {
                if (highestQueuePos > availableSlots)
                {
                    plugin.addUserToLoginQueue(event.getUniqueId());
                    queuePos = plugin.getPositionInQueue(event.getUniqueId());
                    event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, "You're [#" + queuePos + "] in the queue.");
                }
            }
        }
    }
}