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


    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event)
    {
        int playerCount = plugin.getServer().getOnlinePlayers().size();
        int queuePos = plugin.getPositionInQueue(event.getUniqueId());

        if (queuePos > 0)
        {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, "You're [#" + queuePos + "] in the queue.");
        }

        if (plugin.getHighestLoginPos() > 0)
        {

        }
    }

}
