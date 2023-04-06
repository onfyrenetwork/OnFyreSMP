package net.onfyre.onfyresmp.commands.carry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class InvseeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player && player.isOp()) {
            if(args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                player.openInventory(target.getInventory());
            } else player.sendMessage(ChatColor.RED + "Ungültiges Argument!");
        } else sender.sendMessage(ChatColor.RED + "Ungültige Permissions!");
        return false;
    }
}
