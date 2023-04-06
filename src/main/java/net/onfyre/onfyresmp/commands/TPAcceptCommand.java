package net.onfyre.onfyresmp.commands;

import net.onfyre.onfyresmp.Main;
import net.onfyre.onfyresmp.utils.CountdownTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TPAcceptCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(args.length == 1) {
                UUID uniqueId = UUID.fromString(args[0]);
                Player target = Bukkit.getPlayer(uniqueId);
                CountdownTimer timer = new CountdownTimer(Main.getInstance(), 5, () -> {
                    target.teleport(player);
                    target.sendMessage(Main.getPrefix("TPA") + "Du wurdest zu " + player.getName() + " teleportiert!");
                    player.sendMessage(Main.getPrefix("TPA") + target.getName() + " wurde zu dir teleportiert!");
                    target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
                    target.sendTitle(ChatColor.GOLD.toString() + ChatColor.BOLD + "⚡ TPA ⚡", ChatColor.GOLD + "Du befindest dich nun bei: " + ChatColor.GREEN + player.getName());
                }, (t) -> {
                    target.sendMessage(Main.getPrefix("TPA") + "Du wirst in " + t.getSecondsLeft() + " Sekunden zu " + player.getName() + " teleportiert!");
                    player.sendMessage(Main.getPrefix("TPA") + target.getName() + " wird in " + t.getSecondsLeft() + " Sekunden zu dir teleportiert!");
                    target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                });
                timer.scheduleTimer();
            } else sender.sendMessage(ChatColor.RED + "Ungültiges Argument!");
        } else sender.sendMessage(ChatColor.RED + "Ungültige Permissions!");
        return false;
    }
}
