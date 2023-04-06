package net.onfyre.onfyresmp.commands.admin;

import net.onfyre.onfyresmp.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class BanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.isOp()) {
            if(args.length == 2) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                int days = Integer.parseInt(args[1]);
                target.banPlayer(ChatColor.RED + "Du wurdest von " + sender.getName() + " für " + days + " Tage gebannt!", new Date(System.currentTimeMillis() + (days*86400000L)));
                sender.sendMessage(Main.getPrefix() + "Du hast " + target.getName() + " für " + days + " Tage gebannt!");
            } else sender.sendMessage(ChatColor.RED + "Ungültiges Argument!");
        } else sender.sendMessage(ChatColor.RED + "Ungültige Permissions!");
        return false;
    }
}
