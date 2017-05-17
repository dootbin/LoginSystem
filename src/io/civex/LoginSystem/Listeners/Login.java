package io.civex.LoginSystem.Listeners;

import io.civex.LoginSystem.LoginSystemPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

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
    }
}
