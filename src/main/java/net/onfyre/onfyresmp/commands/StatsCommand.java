package net.onfyre.onfyresmp.commands;

import net.onfyre.onfyresmp.Main;
import net.onfyre.onfyresmp.utils.BalanceUtil;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StatsCommand implements CommandExecutor {

    public static Inventory stats = Bukkit.createInventory(null, 3*9, ChatColor.GOLD + "Stats");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            stats.clear();
            stats.setItem(10, head("NoahOnFyre"));
            stats.setItem(11, head("LucaOnFyre"));
            stats.setItem(12, head("DomiOnFyre"));
            stats.setItem(13, head("julianonfyre"));
            stats.setItem(14, head("Lychee9999"));
            stats.setItem(15, head("PedoOnFyre"));
            player.openInventory(stats);
        } else sender.sendMessage(ChatColor.RED + "Ungültige Permissions!");
        return false;
    }

    public ItemStack head(String playerName) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(playerName));
        meta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + playerName);
        meta.setLore(List.of(
                ChatColor.GRAY + "UUID: " + ChatColor.GREEN + Bukkit.getOfflinePlayer(playerName).getUniqueId().toString(),
                ChatColor.GRAY + "Konto: " + ChatColor.GREEN + Main.statsConfig.get(Bukkit.getOfflinePlayer(playerName).getUniqueId() + ".balance") + " Diamanten",
                ChatColor.GRAY + "Spielzeit: " + ChatColor.GREEN + Main.statsConfig.get(Bukkit.getOfflinePlayer(playerName).getUniqueId() + ".playtime") + " Stunden",
                ChatColor.GRAY + "Tode: " + ChatColor.GREEN + Main.statsConfig.get(Bukkit.getOfflinePlayer(playerName).getUniqueId() + ".deaths"),
                ChatColor.GRAY + "Level: " + ChatColor.GREEN + Main.statsConfig.get(Bukkit.getOfflinePlayer(playerName).getUniqueId() + ".level") + " Level"
        ));
        head.setItemMeta(meta);
        return head;
    }
}
