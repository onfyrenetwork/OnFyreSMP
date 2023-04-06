package net.onfyre.onfyresmp.utils;

import net.onfyre.onfyresmp.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class NameUtil {

    public NameUtil(Player player) {
        playerRank(player);
    }

    public void playerRank(Player player) {
        String s;
        if(player.getName().equalsIgnoreCase("NoahOnFyre")) {
            s = "§0§r" + net.md_5.bungee.api.ChatColor.of("#006eff").toString() + "Owner " + ChatColor.RESET + ChatColor.GRAY + "| " + ChatColor.WHITE + player.getName();
        } else {
            s = "§4§r" + ChatColor.GOLD.toString() + "Player " + ChatColor.RESET + ChatColor.GRAY + "| " + ChatColor.WHITE + player.getName();
        }

        player.setDisplayName(s);
        player.setCustomName(s);
        player.setCustomNameVisible(true);
        player.setPlayerListName(s);
    }
}
