package net.onfyre.onfyresmp.commands;

import net.onfyre.onfyresmp.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            player.sendMessage(Main.getPrefix() + "Dein Ping: " + ChatColor.AQUA + player.getPing() + "ms");
        } else sender.sendMessage(ChatColor.RED + "Ungültige Permissions!");
        return false;
    }
}
