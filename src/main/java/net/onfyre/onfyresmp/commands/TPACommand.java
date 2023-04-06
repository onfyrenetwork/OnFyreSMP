package net.onfyre.onfyresmp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

public class TPACommand implements CommandExecutor {

    public static Inventory tpaMenu = Bukkit.createInventory(null, 6*9, ChatColor.GOLD + "Teleportanfrage versenden");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            tpaMenu.clear();
            for(Player allPlayer : Bukkit.getOnlinePlayers()) {
                tpaMenu.addItem(getHead(allPlayer.getName()));
            }
            player.openInventory(tpaMenu);
        }
        return false;
    }

    public ItemStack getHead(String playerName) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(playerName));
        meta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + playerName);
        head.setItemMeta(meta);
        return head;
    }
}
