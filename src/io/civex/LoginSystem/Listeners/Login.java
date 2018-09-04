package io.civex.LoginSystem.Listeners;

import io.civex.LoginSystem.LoginSystemPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

/**
 * Created by Ryan on 5/15/2017.
 */
public class Login implements Listener
{
    LoginSystemPlugin plugin;

    public Login(LoginSystemPlugin pl)
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
        if (highestQueuePos > 0)
        {
            if (queuePos > 0)
            {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You're [#" + queuePos + "] in the queue. Please connect again in a bit.");
            }
            else
            {
                plugin.addUserToLoginQueue(event.getPlayer().getUniqueId(), event.getPlayer().getName());
                queuePos = plugin.getPositionInQueue(event.getPlayer().getUniqueId());
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You're [#" + queuePos + "] in the queue. Please connect again in a bit.");
            }
        }
        else if (playerCount >= maxPlayerCount)
        {
            if (queuePos > 0)
            {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You're [#" + queuePos + "] in the queue. Please connect again in a bit.");
            }
            else
            {
                plugin.addUserToLoginQueue(event.getPlayer().getUniqueId(), event.getPlayer().getName());
                queuePos = plugin.getPositionInQueue(event.getPlayer().getUniqueId());
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You're [#" + queuePos + "] in the queue. Please connect again in a bit.");
            }
        }
        else if (availableSlots > 0)
        {
            if (queuePos > 0)
            {
                if (queuePos > availableSlots)
                {
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You're [#" + queuePos + "] in the queue. Please connect again in a bit.");
                }
            }
            else
            {
                if (highestQueuePos > availableSlots)
                {
                    plugin.addUserToLoginQueue(event.getPlayer().getUniqueId(), event.getPlayer().getName());
                    queuePos = plugin.getPositionInQueue(event.getPlayer().getUniqueId());
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You're [#" + queuePos + "] in the queue. Please connect again in a bit.");
                }
            }
        }
        else
        {
            if (availableSlots == 0)
            {
                plugin.addUserToLoginQueue(event.getPlayer().getUniqueId(), event.getPlayer().getName());
                queuePos = plugin.getPositionInQueue(event.getPlayer().getUniqueId());
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You're [#" + queuePos + "] in the queue. Please connect again in a bit.");
            }
        }

        if (event.getPlayer().hasPermission("civex.queue.bypass"))
        {
            event.allow();
        }
    }
}
