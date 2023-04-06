package net.onfyre.onfyresmp.ui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Tablist {

    public Tablist(@NotNull Player player, @NotNull Server server) {
        String header = ChatColor.GOLD.toString() + ChatColor.BOLD + "OnFyreSMP" + ChatColor.RESET + ChatColor.DARK_GRAY + " - " + ChatColor.AQUA + "Paper " + Bukkit.getMinecraftVersion() + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + server.getOnlinePlayers().size() + " von " + server.getMaxPlayers() + " Spielern Online!";

        String footer1 = ChatColor.GOLD.toString() + "Dein Ping" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GREEN + player.getPing();
        String footer2 = ChatColor.GOLD.toString() + "Account" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GREEN + player.getName();
        String footer3 = ChatColor.GOLD.toString() + "Deine Tode" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GREEN + player.getStatistic(Statistic.DEATHS);

        player.setPlayerListHeaderFooter("\n  " + header + "  \n", "\n  " + footer1 + "\n" + footer2 + "\n" + footer3 + "\n  ");
    }
}