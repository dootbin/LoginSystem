package io.civex.LoginSystem.Listeners;

import io.civex.LoginSystem.LoginQueue;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Created by Ryan on 5/15/2017.
 */
public class Login implements Listener
{
    private LoginQueue plugin;

    public Login(LoginQueue pl)
    {
        this.plugin = pl;
    }

    //if slots available >= your queue pos allow in.
    @EventHandler
    public void onLogin(PlayerLoginEvent event)
    {
        int playerCount = plugin.getServer().getOnlinePlayers().size();
        int maxPlayerCount = plugin.getServer().getMaxPlayers();
        int availableSlots = maxPlayerCount - playerCount;
        int queuePos = plugin.getPositionInQueue(event.getPlayer().getUniqueId());
        int highestQueuePos = plugin.getHighestQueuePos();

        if (!plugin.loginQueueProgressing)
        {
            availableSlots = 0;
        }

        // If user is on the clock, we know they should be able to log in, even if someone bypassed the queue
        if (plugin.isOnTheClock(event.getPlayer().getUniqueId())) {
            return;
        }

        if (availableSlots > 0)
        {
            if (queuePos > 0)
            {
                if (queuePos > availableSlots)
                {
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You're [#" + queuePos + "] in the queue. Please connect again in a bit.");
                }

            }
            else if (highestQueuePos >= availableSlots)
            {
                plugin.addUserToLoginQueue(event.getPlayer(), event.getAddress().getHostAddress());
                queuePos = plugin.getPositionInQueue(event.getPlayer().getUniqueId());
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You're [#" + queuePos + "] in the queue. Please connect again in a bit.");
            }
        }
        else
        {
            if (queuePos > 0)
            {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You're [#" + queuePos + "] in the queue. Please connect again in a bit.");
            }
            else
            {
                plugin.addUserToLoginQueue(event.getPlayer(), event.getAddress().getHostAddress());
                queuePos = plugin.getPositionInQueue(event.getPlayer().getUniqueId());
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You're [#" + queuePos + "] in the queue. Please connect again in a bit.");
            }
        }


        // Bypass
        if (event.getPlayer().hasPermission("civex.queue.bypass"))
        {
            event.allow();
        }
    }
}
