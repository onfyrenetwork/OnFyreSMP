package net.onfyre.onfyresmp.commands;

import net.onfyre.onfyresmp.Main;
import net.onfyre.onfyresmp.listeners.MainListener;
import net.onfyre.onfyresmp.utils.CountdownTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(!MainListener.inCombat.contains(player)) {
                CountdownTimer timer = new CountdownTimer(Main.getInstance(),5, () -> {
                    player.sendMessage(Main.getPrefix() + "Du wurdest zum Spawn gewarpt!");
                    player.sendTitle(ChatColor.GOLD.toString() + ChatColor.BOLD + "⚡ Warps ⚡", ChatColor.GOLD + "Du befindest dich nun bei: " + ChatColor.GREEN + "Spawn");
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> player.teleport(new Location(Bukkit.getWorld("world"), 0.50, 280, 0.50)), 1L);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
                    player.setInvulnerable(false);
                }, (t) -> {
                    player.sendMessage(Main.getPrefix() + "Du wirst in " + t.getSecondsLeft() + " Sekunden zu " + ChatColor.GREEN + "Spawn " + ChatColor.GOLD + "teleportiert.");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                });
                timer.scheduleTimer();
                player.setInvulnerable(true);
            } else player.sendMessage(ChatColor.RED + "Du kannst im Combat nicht das Warp-System nutzen.");
        } else sender.sendMessage(ChatColor.RED + "Ungültige Permissions!");
        return false;
    }
}
