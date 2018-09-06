package io.civex.LoginSystem.Listeners;

import io.civex.LoginSystem.LoginQueue;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Ryan on 5/16/2017.
 */
public class Logout implements Listener
{
    private LoginQueue plugin;

    public Logout(LoginQueue plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event)
    {
        plugin.checkIfUsersShouldBeOnClock(1);
    }
}