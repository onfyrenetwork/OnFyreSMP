package net.onfyre.onfyresmp.commands.admin;

import net.onfyre.onfyresmp.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KickCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.isOp()) {
            if(args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                target.kickPlayer(ChatColor.RED + "Du wurdest von " + sender.getName() + " gekickt!");
                sender.sendMessage(Main.getPrefix() + "Du hast " + target.getName() + " gekickt!");
            } else sender.sendMessage(ChatColor.RED + "Ungültiges Argument!");
        } else sender.sendMessage(ChatColor.RED + "Ungültige Permissions!");
        return false;
    }
}
