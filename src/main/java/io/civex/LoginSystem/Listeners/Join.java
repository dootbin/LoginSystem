package io.civex.LoginSystem.Listeners;

import io.civex.LoginSystem.LoginSystemPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Created by Ryan on 5/16/2017.
 */
public class Join implements Listener
{
    LoginSystemPlugin plugin;

    public Join(LoginSystemPlugin pl)
    {
        this.plugin = pl;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player p = event.getPlayer();

        int queuePos = plugin.getPositionInQueue(p.getUniqueId());

        if (queuePos > 0)
        {
            plugin.removeUserAtPos(queuePos);
        }

        plugin.checkIfUsersShouldBeOnClock();
    }
}
