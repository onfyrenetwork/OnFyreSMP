package net.onfyre.onfyresmp.commands.admin;

import net.onfyre.onfyresmp.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlySpeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.isOp() && sender instanceof Player player) {
            if(args.length == 1) {
                float speed = Float.parseFloat(args[0]);
                player.setFlySpeed(speed);
            } else sender.sendMessage(ChatColor.RED + "Ungültiges Argument!");
        } else sender.sendMessage(ChatColor.RED + "Ungültige Permissions!");
        return false;
    }
}
