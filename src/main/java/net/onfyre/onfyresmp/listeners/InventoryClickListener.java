package net.onfyre.onfyresmp.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.onfyre.onfyresmp.Main;
import net.onfyre.onfyresmp.commands.StatsCommand;
import net.onfyre.onfyresmp.commands.TPACommand;
import net.onfyre.onfyresmp.commands.admin.AcpCommand;
import net.onfyre.onfyresmp.utils.CountdownTimer;
import net.onfyre.onfyresmp.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().equals(StatsCommand.stats)) {
            event.setCancelled(true);
        } else if (event.getInventory().equals(AcpCommand.adminPanel)) {
            event.setCancelled(true);
            Server server = player.getServer();
            if (event.getSlot() == 10) {
                player.closeInventory();
                Bukkit.broadcastMessage(Main.getPrefix() + "Server wird neu geladen...");
                server.reload();
                Bukkit.broadcastMessage(Main.getPrefix() + "Befehle werden neu geladen...");
                server.reloadCommandAliases();
                Bukkit.broadcastMessage(Main.getPrefix() + "Daten werden neu geladen...");
                server.reloadData();
                Bukkit.broadcastMessage(Main.getPrefix() + "Permissions werden neu geladen...");
                server.reloadPermissions();
                Bukkit.broadcastMessage(Main.getPrefix() + "Whitelist werden neu geladen...");
                server.reloadWhitelist();
                Bukkit.broadcastMessage(Main.getPrefix() + "Reload abgeschlossen!");
            } else if (event.getSlot() == 12) {
                player.closeInventory();

                CountdownTimer timer = new CountdownTimer(Main.getInstance(),30, () -> {
                    for (Player allPlayer : Bukkit.getOnlinePlayers()) {
                        allPlayer.kickPlayer(Main.getPrefix() + "Der Server wurde neugestartet! " + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Forced" + ChatColor.DARK_GRAY + "]");
                    }
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
                }, (t) -> {
                    Bukkit.broadcastMessage(Main.getPrefix() + "Der Server wird in " + ChatColor.GREEN + t.getSecondsLeft() + " Sekunden" + ChatColor.GOLD + " neugestartet!");
                    for(Player allPlayer : Bukkit.getOnlinePlayers()) {
                        allPlayer.playSound(allPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                    }
                });
                timer.scheduleTimer();
            } else if (event.getSlot() == 14) {
                player.closeInventory();
                CountdownTimer timer = new CountdownTimer(Main.getInstance(),30, () -> {
                    for (Player allPlayer : Bukkit.getOnlinePlayers()) {
                        allPlayer.kickPlayer(Main.getPrefix() + "Der Server wurde geschlossen! " + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Forced" + ChatColor.DARK_GRAY + "]");
                    }
                    server.shutdown();
                }, (t) -> {
                    Bukkit.broadcastMessage(Main.getPrefix() + "Der Server wird in " + ChatColor.GREEN + t.getSecondsLeft() + " Sekunden" + ChatColor.GOLD + " geschlossen!");
                    for(Player allPlayer : Bukkit.getOnlinePlayers()) {
                        allPlayer.playSound(allPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                    }
                });
                timer.scheduleTimer();
            } else if (event.getSlot() == 16) {
                player.closeInventory();
                for (Player allPlayer : Bukkit.getOnlinePlayers()) {
                    allPlayer.kickPlayer(Main.getPrefix() + "Der Server wurde geschlossen. " + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Forced" + ChatColor.DARK_GRAY + "]");
                }
                server.shutdown();
            } else if (event.getSlot() == 28) {
                player.closeInventory();
                for (Player allPlayer : Bukkit.getOnlinePlayers()) {
                    allPlayer.kickPlayer(Main.getPrefix() + "Der Server wurde aufgrund von 'Plugin-Update' geschlossen! " + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Forced" + ChatColor.DARK_GRAY + "]");
                }
                server.shutdown();
            } else if (event.getSlot() == 30) {
                player.closeInventory();
                for (Player allPlayer : Bukkit.getOnlinePlayers()) {
                    allPlayer.kickPlayer(Main.getPrefix() + "Der Server wurde aufgrund von 'Wartungsarbeiten' geschlossen! " + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Forced" + ChatColor.DARK_GRAY + "]");
                }
                Main.isBlocked = true;
            }
        } else if(event.getInventory().equals(TPACommand.tpaMenu)) {
            event.setCancelled(true);
            Player requested = Bukkit.getPlayer(event.getInventory().getItem(event.getSlot()).getItemMeta().getDisplayName().replace(ChatColor.GOLD.toString() + ChatColor.BOLD, ""));
            if (requested == null) {
                player.sendMessage(Main.getPrefix() + "Der Spieler wurde nicht gefunden!");
                return;
            }
            TextComponent component = Component.text(ChatColor.GOLD + player.getName() + " hat dir eine Teleportanfrage geschickt! Klicke um zu akzeptieren.").clickEvent(ClickEvent.runCommand("/tpaccept " + player.getUniqueId().toString()));
            requested.sendMessage(component);
            player.closeInventory();
        }
    }
}
