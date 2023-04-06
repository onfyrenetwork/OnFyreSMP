package net.onfyre.onfyresmp.commands.admin;

import net.onfyre.onfyresmp.Main;
import net.onfyre.onfyresmp.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AcpCommand implements CommandExecutor {

    public static Inventory adminPanel = Bukkit.createInventory(null, 5*9, ChatColor.GOLD + "Admin Control Panel");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.isOp() && sender instanceof Player player) {
            if(args.length != 0) {
                player.sendMessage(Main.getPrefix() + "ACP Session mit den Argumenten " + args[0] + " gestartet! ");
            } else {
                player.sendMessage(Main.getPrefix() + "ACP Session gestartet!");
            }
            adminPanel.setItem(10, new ItemBuilder(Material.SLIME_BALL).setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Server neuladen (Kann zu Fehlern führen)").build());
            adminPanel.setItem(12, new ItemBuilder(Material.MAGMA_CREAM).setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Server neustarten").build());
            adminPanel.setItem(14, new ItemBuilder(Material.FIRE_CHARGE).setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Server schließen").build());
            adminPanel.setItem(14, new ItemBuilder(Material.FIRE_CHARGE).setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Server mit angegebenen Grund schließen").build());
            adminPanel.setItem(28, new ItemBuilder(Material.GLOWSTONE_DUST).setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Plugins Updaten").build());
            adminPanel.setItem(30, new ItemBuilder(Material.REDSTONE).setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Wartungsmodus").build());
            adminPanel.setItem(32, new ItemBuilder(Material.GUNPOWDER).setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Wartungsmodus").build());
            player.openInventory(adminPanel);
        } else sender.sendMessage(ChatColor.RED + "Ungültige Permissions!");
        return false;
    }
}
