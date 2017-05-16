package io.civex.LoginSystem.Utils;

import io.civex.LoginSystem.LoginSystem;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/**
 * Created by Ryan on 5/16/2017.
 */
public class LoginTimeRunnable extends BukkitRunnable
{
    private LoginSystem plugin;
    private UUID player;

    public LoginTimeRunnable(LoginSystem plugin, UUID player)
    {
        this.plugin = plugin;
        this.player = player;
    }

    @Override
    public void run()
    {
        if (plugin.getUserInPosition(1) != null)
        {
            if (plugin.getUserInPosition(1) == player)
            {
                plugin.removeFirstUser();
            }
        }
    }
}
