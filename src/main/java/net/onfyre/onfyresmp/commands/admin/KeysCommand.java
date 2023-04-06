package net.onfyre.onfyresmp.commands.admin;

import net.onfyre.onfyresmp.Main;
import net.onfyre.onfyresmp.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class KeysCommand implements CommandExecutor {

    public static Inventory keyMenu = Bukkit.createInventory(null, 3*9, ChatColor.GOLD + "Keyauswahl");
    public static ItemStack starterKey = new ItemBuilder(Material.TRIPWIRE_HOOK).setDisplayName(ChatColor.RED + "Starter Key").setAmount(1).build();
    public static ItemStack rewardKey = new ItemBuilder(Material.TRIPWIRE_HOOK).setDisplayName(ChatColor.GREEN + "Reward Key").setAmount(1).build();
    public static ItemStack pvpKey = new ItemBuilder(Material.TRIPWIRE_HOOK).setDisplayName(ChatColor.DARK_PURPLE + "PvP Key").setAmount(1).build();
    public static ItemStack magicKey = new ItemBuilder(Material.TRIPWIRE_HOOK).setDisplayName(ChatColor.BLACK + "Magic Key").setAmount(1).build();
    public static ItemStack godKey = new ItemBuilder(Material.TRIPWIRE_HOOK).setDisplayName(ChatColor.YELLOW + "God Key").setAmount(1).build();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.isOp() && sender instanceof Player player) {
            keyMenu.setItem(10, starterKey);
            keyMenu.setItem(11, rewardKey);
            keyMenu.setItem(12, pvpKey);
            keyMenu.setItem(13, magicKey);
            keyMenu.setItem(14, godKey);
            player.openInventory(keyMenu);
        } else sender.sendMessage(ChatColor.RED + "Ungültige Permissions!");
        return false;
    }
}
