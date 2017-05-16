package io.civex.LoginSystem.Listeners;

import io.civex.LoginSystem.LoginSystem;
import io.civex.LoginSystem.Utils.LoginTimeRunnable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Ryan on 5/16/2017.
 */
public class Logout implements Listener
{
    LoginSystem plugin;

    public Logout(LoginSystem plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event)
    {
        if (plugin.getHighestQueuePos() > 0)
        {
            if (plugin.getUserInPosition(1) != null)
            {
                new LoginTimeRunnable(plugin, plugin.getUserInPosition(1)).runTaskLater(plugin, plugin.allowedConnectTime * 19L);
            }
        }
    }

}
