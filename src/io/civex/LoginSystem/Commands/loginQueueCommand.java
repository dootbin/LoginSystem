package io.civex.LoginSystem.Commands;

import io.civex.LoginSystem.LoginSystemPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Ryan on 5/16/2017.
 */
public class loginQueueCommand implements CommandExecutor
{
    LoginSystemPlugin plugin;

    public loginQueueCommand(LoginSystemPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;

            if (args.length == 0)
            {
                sender.sendMessage(ChatColor.AQUA + "Please try putting something after the command like status.");
                return true;
            }

            //This is to stop people from logging in (usually for debug when making changes to this plugin)
            if (args[0].equalsIgnoreCase("stop"))
            {
                if (!player.hasPermission("civex.queue.stopQueue") || !player.isOp())
                {
                    sendNoPermission(player);
                    return true;
                }

                plugin.loginQueueProgressing = false;
                sendChangeQueueStateMessage(player);
                return true;
            }

            if (args[0].equalsIgnoreCase("start"))
            {
                if (!player.hasPermission("civex.queue.stopQueue") || !player.isOp())
                {
                    sendNoPermission(player);
                    return true;
                }

                plugin.loginQueueProgressing = true;
                sendChangeQueueStateMessage(player);
                return true;
            }

            if (args[0].equalsIgnoreCase("toggle"))
            {
                if (!player.hasPermission("civex.queue.stopQueue") || !player.isOp())
                {
                    sendNoPermission(player);
                    return true;
                }

                plugin.loginQueueProgressing = !plugin.loginQueueProgressing;
                sendChangeQueueStateMessage(player);
                return true;
            }

            if (args[0].equalsIgnoreCase("reset"))
            {
                if (!player.hasPermission("civex.queue.restart") || !player.isOp())
                {
                    sendNoPermission(player);
                    return true;
                }

                plugin.resetQueue();
                player.sendMessage(ChatColor.RED + "YOU HAVE RESET THE QUEUE");
                return true;
            }

            if (args[0].equalsIgnoreCase("remove"))
            {
                if (!player.hasPermission("civex.queue.remove") || !player.isOp())
                {
                    sendNoPermission(player);
                    return true;
                }

                if (args.length > 1)
                {
                    int personToRemove = 0;
                    try
                    {
                        personToRemove = Integer.parseInt(args[1]);
                    }
                    catch (Exception ex)
                    {
                        player.sendMessage(ChatColor.RED + "Could not parse your int. Please use /queue remove INT");
                        return true;
                    }

                    if (personToRemove > 0)
                    {
                        plugin.removeUserAtPos(personToRemove);
                        player.sendMessage(ChatColor.AQUA + "You have removed [" + ChatColor.WHITE +
                                plugin.getNameFromUUID(plugin.getUserInPosition(personToRemove)) + ChatColor.AQUA +
                                "][pos" + ChatColor.WHITE + personToRemove + ChatColor.AQUA + "] from the queue.");
                        return true;
                    }
                }
            }

            if (args[0].equalsIgnoreCase("status") || args[0].equalsIgnoreCase("list"))
            {
                sendLoginQueueStatus(player);
                return true;
            }
        }

        sender.sendMessage(ChatColor.AQUA + "Please try putting something after the command like status.");
        return true;
    }

    private void sendNoPermission(Player player)
    {
        player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
    }

    private void sendChangeQueueStateMessage(Player player)
    {
        if (plugin.loginQueueProgressing)
        {
            player.sendMessage(ChatColor.AQUA + "You have [" + ChatColor.GREEN + "STARTED" + ChatColor.AQUA + "] the login queue.");
        }
        else
        {
            player.sendMessage(ChatColor.AQUA + "You have [" + ChatColor.RED + "STOPPED" + ChatColor.AQUA + "] the login queue.");
        }
    }

    private void sendLoginQueueStatus(Player player)
    {
        int highPos = plugin.getHighestQueuePos();
        int playerCount = plugin.getServer().getOnlinePlayers().size();
        int maxCount = plugin.getServer().getMaxPlayers();
        int slots = maxCount - playerCount;

        if (highPos > 0)
        {
            if (highPos > 1)
            {
                player.sendMessage(ChatColor.AQUA + "The login queue has [" + highPos + "] people in it. There are [" + slots + "] slots [" + playerCount + "/" + maxCount + "]");
            }
            else
            {
                player.sendMessage(ChatColor.AQUA + "The login queue has [" + highPos + "] person in it. There are [" + slots + "] slots. [" + playerCount + "/" + maxCount + "]");
            }


            if (plugin.showedPlayersInStatus > 0)
            {
                for (int i = 1; i <= 5; i++)
                {
                    if (i <= highPos)
                    {
                        if (plugin.getUserInPosition(i) != null)
                        {
                            UUID queuePerson = plugin.getUserInPosition(i);
                            sendPlayerPosInfoStatusMessage(player, plugin.getNameFromUUID(queuePerson), i, queuePerson);
                        }
                    }
                }
            }
        }
        else
        {
            player.sendMessage(ChatColor.AQUA + "There is no one in the queue.");
        }
    }

    private String slotsAndPlayerCount(int slots, int playerCount, int maxCount)
    {
        if (slots > 1)
        {
            return "There are [" + slots + "] slots [" + playerCount + "/" + maxCount + "]";
        }
        else if (slots == 1)
        {
            return "There is [" + slots + "] slot [" + playerCount + "/" + maxCount + "]";
        }
        else
        {
            return "There are [NO] slots [" + playerCount + "/" + maxCount + "]";
        }
    }

    private void sendPlayerPosInfoStatusMessage(Player receiver, String playerInQueueName, int posInQueue, UUID uuid)
    {
        if (plugin.isOnTheClock(uuid))
        {
            receiver.sendMessage(ChatColor.RED + "  [" + posInQueue + "] : " + playerInQueueName + " is on the clock");
        }
        else
        {
            receiver.sendMessage(ChatColor.AQUA + "  [" + posInQueue + "] : " + playerInQueueName);
        }

    }
}