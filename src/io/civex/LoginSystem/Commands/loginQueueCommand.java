package io.civex.LoginSystem.Commands;

import io.civex.LoginSystem.LoginSystem;
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
    LoginSystem plugin;

    public loginQueueCommand(LoginSystem plugin)
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
                if (!player.hasPermission("civex.stopQueue") || !player.isOp())
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
                if (!player.hasPermission("civex.stopQueue") || !player.isOp())
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
                if (!player.hasPermission("civex.stopQueue") || !player.isOp())
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

            }
        }

        return false;
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
        }

    }
}
