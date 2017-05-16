package io.civex.LoginSystem.Listeners;

import io.civex.LoginSystem.LoginSystem;
import io.civex.LoginSystem.Utils.LoginTimeRunnable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

/**
 * Created by Ryan on 5/16/2017.
 */
public class Login implements Listener
{
    LoginSystem plugin;

    public Login(LoginSystem pl)
    {
        this.plugin = pl;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event)
    {
        Player p = event.getPlayer();
        if (plugin.getPositionInQueue(p.getUniqueId()) == 1)
        {
            plugin.removeFirstUser();
        }
    }
}
