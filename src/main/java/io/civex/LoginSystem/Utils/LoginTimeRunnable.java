package io.civex.LoginSystem.Utils;

import io.civex.LoginSystem.LoginSystemPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/**
 * Created by Ryan on 5/16/2017.
 */
public class LoginTimeRunnable extends BukkitRunnable
{
    private LoginSystemPlugin plugin;
    private UUID player;

    public LoginTimeRunnable(LoginSystemPlugin plugin, UUID player)
    {
        this.plugin = plugin;
        this.player = player;
    }

    @Override
    public void run()
    {
        int queuePos = plugin.getPositionInQueue(player);

        if (queuePos > 0)
        {
            plugin.removeUserAtPos(queuePos);
            plugin.checkIfUsersShouldBeOnClock();
        }
    }
}
