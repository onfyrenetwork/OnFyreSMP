package net.onfyre.onfyresmp.commands.admin;

import net.onfyre.onfyresmp.Main;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UnbanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.isOp()) {
            if(args.length == 1) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                BanList banList = sender.getServer().getBanList(BanList.Type.NAME);
                banList.pardon(target.getName());
                sender.sendMessage(Main.getPrefix() + "Du hast " + target.getName() + " entbannt!");
            } else sender.sendMessage(ChatColor.RED + "Ungültiges Argument!");
        } else sender.sendMessage(ChatColor.RED + "Ungültige Permissions!");
        return false;
    }
}
