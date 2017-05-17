package io.civex.LoginSystem.Commands;

import io.civex.LoginSystem.LoginSystemPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

            if (args[0].equalsIgnoreCase("status"))
            {
                sendLoginQueueStatus(player);
            }
        }

        sender.sendMessage("Please try putting something after the command like status.");
        sender.sendMessage("Args[0] = " + args[0] + ".");
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
        if (highPos > 0)
        {
            player.sendMessage(ChatColor.AQUA + "The login queue has [" + highPos + "] people in it.");

            if (plugin.showedPlayersInStatus > 0)
            {
                for (int i = 1; i <= 5; i++)
                {
                    if (i <= highPos)
                    {
                        if (plugin.getUserInPosition(i) != null)
                        {
                            sendPlayerPosInfoStatusMessage(player, plugin.getNameFromUUID(plugin.getUserInPosition(i)), i);
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

    private void sendPlayerPosInfoStatusMessage(Player receiver, String playerInQueueName, int posInQueue)
    {
        receiver.sendMessage(ChatColor.AQUA + "  [" + posInQueue + "] : " + playerInQueueName);
    }

}
