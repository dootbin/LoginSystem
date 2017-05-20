package io.civex.LoginSystem.Listeners;

import io.civex.LoginSystem.LoginSystemPlugin;
import io.civex.LoginSystem.Utils.LoginTimeRunnable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.UUID;

/**
 * Created by Ryan on 5/16/2017.
 */
public class Login implements Listener
{
    LoginSystemPlugin plugin;

    public Login(LoginSystemPlugin pl)
    {
        this.plugin = pl;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event)
    {
        Player p = event.getPlayer();

        int queuePos = plugin.getPositionInQueue(p.getUniqueId());

        if (queuePos > 0)
        {
            plugin.removeUserAtPos(queuePos);
        }

        int availableSlots = plugin.getServer().getMaxPlayers() - plugin.getServer().getOnlinePlayers().size();
        availableSlots--;

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
