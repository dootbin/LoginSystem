package io.civex.LoginSystem.Listeners;

import io.civex.LoginSystem.LoginSystemPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPreLoginEvent;

/**
 * Created by Ryan on 5/15/2017.
 */
public class PreLogin implements Listener
{
    LoginSystemPlugin plugin;

    public PreLogin(LoginSystemPlugin pl)
    {
        this.plugin = pl;
    }

    //if slots available >= your queue pos allow in.
    @EventHandler
    public void onPreLogin(PlayerPreLoginEvent event)
    {
        int playerCount = plugin.getServer().getOnlinePlayers().size();
        int maxPlayerCount = plugin.getServer().getMaxPlayers();
        int availableSlots = maxPlayerCount - playerCount;
        int queuePos = plugin.getPositionInQueue(event.getUniqueId());
        int highestQueuePos = plugin.getHighestQueuePos();

        if (!plugin.loginQueueProgressing)
        {
            availableSlots = 0;
        }

        if (playerCount >= maxPlayerCount)
        {
            if (queuePos > 0)
            {
                event.disallow(PlayerPreLoginEvent.Result.KICK_FULL, "You're [#" + queuePos + "] in the queue. Please connect again in a bit.");
            }
            else
            {
                plugin.addUserToLoginQueue(event.getUniqueId(), event.getName());
                queuePos = plugin.getPositionInQueue(event.getUniqueId());
                event.disallow(PlayerPreLoginEvent.Result.KICK_FULL, "You're [#" + queuePos + "] in the queue. Please connect again in a bit.");
            }
        }
        else if (availableSlots > 0)
        {
            if (queuePos > 0)
            {
                if (queuePos > availableSlots)
                {
                    event.disallow(PlayerPreLoginEvent.Result.KICK_FULL, "You're [#" + queuePos + "] in the queue. Please connect again in a bit.");
                }
            }
            else
            {
                if (highestQueuePos > availableSlots)
                {
                    plugin.addUserToLoginQueue(event.getUniqueId(), event.getName());
                    queuePos = plugin.getPositionInQueue(event.getUniqueId());
                    event.disallow(PlayerPreLoginEvent.Result.KICK_FULL, "You're [#" + queuePos + "] in the queue. Please connect again in a bit.");
                }
            }
        }
        else
        {
            if (availableSlots == 0)
            {
                plugin.addUserToLoginQueue(event.getUniqueId(), event.getName());
                queuePos = plugin.getPositionInQueue(event.getUniqueId());
                event.disallow(PlayerPreLoginEvent.Result.KICK_FULL, "You're [#" + queuePos + "] in the queue. Please connect again in a bit.");
            }
        }

        if (event.getUniqueId().toString().equalsIgnoreCase("ffd78cb6-4467-4d95-af52-4bb66386a607")//ryan
                || event.getUniqueId().toString().equalsIgnoreCase("ffd78cb6-4467-4d95-af52-4bb66386a607"))//epsilon
        {
            event.allow();
        }

    }
}