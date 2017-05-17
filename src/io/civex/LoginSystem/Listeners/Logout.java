package io.civex.LoginSystem.Listeners;

import io.civex.LoginSystem.LoginSystemPlugin;
import io.civex.LoginSystem.Utils.LoginTimeRunnable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * Created by Ryan on 5/16/2017.
 */
public class Logout implements Listener
{
    LoginSystemPlugin plugin;

    public Logout(LoginSystemPlugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event)
    {
        int availableSlots = plugin.getServer().getMaxPlayers() - plugin.getServer().getOnlinePlayers().size();

        if (availableSlots > 0)
        {
            for (int i = availableSlots; i > 0; i--)
            {
                if (plugin.getUserInPosition(i) != null)
                {
                    putPlayerOnTheClock(plugin.getUserInPosition(i));
                }
            }
        }
    }

    private void putPlayerOnTheClock(UUID p)
    {
        if (!plugin.isOnTheClock(p))
        {
            plugin.addUserToOnTheClock(p);
            new LoginTimeRunnable(plugin, p).runTaskLater(plugin, plugin.allowedConnectTime * 19L);
        }
    }
}