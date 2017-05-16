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
    private int highestQueuePos;

    public boolean loginQueueProgressing = true;
    public int allowedConnectTime = 45;

    @Override
    public void onEnable()
    {
        loginQueue = HashBiMap.create();
        highestQueuePos = 0;
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
            loginQueue.put(p, ++highestQueuePos);
        }
    }

    public synchronized UUID getUserInPosition(int pos)
    {
        if (loginQueue.inverse().containsKey(pos))
        {
            return loginQueue.inverse().get(pos);
        }
        else
        {
            return null;
        }

    }

    public synchronized void removeFirstUser(int personBeingRemoved)
    {
        HashBiMap<UUID, Integer> newMap = HashBiMap.create();
        loginQueue.inverse().remove(personBeingRemoved);

        for (UUID player : loginQueue.keySet())
        {
            int pos = loginQueue.get(player);

            if(pos > personBeingRemoved)
            {
                pos--;
            }

            newMap.put(player, pos);
        }

        highestQueuePos--;
        loginQueue = newMap;
    }

    public synchronized void removeUserAtPos()
    {

    }

    public synchronized int getHighestQueuePos()
    {
        return highestQueuePos;
    }

}