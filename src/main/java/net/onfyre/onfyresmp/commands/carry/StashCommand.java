package net.onfyre.onfyresmp.commands.carry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class StashCommand implements CommandExecutor {

    public static Inventory publicStash = Bukkit.createInventory(null, 6*9, ChatColor.GOLD + "Public Stash");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            player.openInventory(publicStash);
        }
        return false;
    }
}
