package io.civex.LoginSystem;

import com.google.common.collect.HashBiMap;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.UUID;

/**
 * Created by Ryan on 5/15/2017.
 */
public class LoginSystem extends JavaPlugin
{
    private HashBiMap<UUID, Integer> loginQueue;
    private int highestLoginPos;

    @Override
    public void onEnable()
    {
        loginQueue = HashBiMap.create();
        highestLoginPos = 0;
    }

    @Override
    public void onDisable()
    {

    }

    public synchronized int getPositionInQueue(UUID p)
    {
        if (loginQueue.containsKey(p))
        {
            return loginQueue.get(p);
        }
        else
        {
            return 0;
        }
    }

    public synchronized void addUserToLoginQueue(UUID p)
    {
        if (!loginQueue.containsKey(p))
        {
            loginQueue.put(p, ++highestLoginPos);
        }
    }

    public synchronized UUID getUserInPosition(int pos)
    {
        return loginQueue.inverse().get(pos);
    }

    public synchronized void removeFirstUser()
    {
        HashBiMap<UUID, Integer> newMap = HashBiMap.create();
        loginQueue.inverse().remove(1);

        for (UUID player : loginQueue.keySet())
        {
            int pos = loginQueue.get(player) - 1;
            newMap.put(player, pos);
        }

        highestLoginPos--;
        loginQueue = newMap;
    }

    public synchronized int getHighestLoginPos()
    {
        return highestLoginPos;
    }

}