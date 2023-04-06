package net.onfyre.onfyresmp.listeners;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.onfyre.onfyresmp.Main;
import net.onfyre.onfyresmp.ui.MainScoreBoard;
import net.onfyre.onfyresmp.ui.Tablist;
import net.onfyre.onfyresmp.utils.ItemBuilder;
import net.onfyre.onfyresmp.utils.NameUtil;
import net.onfyre.onfyresmp.utils.StringFormatUtil;
import org.bukkit.*;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.raid.RaidSpawnWaveEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class MainListener implements Listener {

    public static ArrayList<Player> inCombat = new ArrayList<>();

    @EventHandler
    public void onPreJoin(AsyncPlayerPreLoginEvent event) {
        if(Main.isBlocked) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Main.getPrefix() + "Der Server wurde aufgrund von 'Wartungsarbeiten' geschlossen!\n" + ChatColor.GREEN + "Bei Fragen kannst du gern auf unserem Discord nachfragen: " + ChatColor.BLUE + "discord.io/onfyre");
        }
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        if(Main.isBlocked) {
            event.setMotd(ChatColor.DARK_RED + "Server is currently in maintenance.\nPlease contact the server administrators");
        } else {
            event.setMotd(ChatColor.GOLD.toString() + ChatColor.BOLD + "OnFyreSMP" + ChatColor.RESET + ChatColor.DARK_GRAY + " - " + ChatColor.AQUA + "Season I" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + event.getNumPlayers() + " von " + event.getMaxPlayers() + "\n" + ChatColor.DARK_PURPLE + "UPDATE: " + ChatColor.LIGHT_PURPLE + "THE VANILLA EXPIERIENCE");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ExperienceOrb orb = player.getWorld().spawn(player.getLocation(), ExperienceOrb.class);
        orb.setExperience(20);
        event.setJoinMessage(Main.getPrefix("+") + player.getName());
        TextComponent actionBar = Component.text(ChatColor.GOLD + "Willkommen zurück, " + player.getName() + "!");
        player.setInvulnerable(true);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> player.setInvulnerable(false), 100L);
        player.sendActionBar(actionBar);
        player.sendTitle(ChatColor.GOLD + "Hallo, " + player.getName() + "!", ChatColor.GOLD + "Du hast " + getPlaytime(player) + " Spielstunden auf diesem Account.", 10, 120, 20);
        new NameUtil(player);
        new Tablist(player, player.getServer());
        MainScoreBoard.setScoreBoard(player);
        if(player.getGameMode().equals(GameMode.SPECTATOR)) {
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(Main.getPrefix() + "Du hast dich im GhostMode ausgeloggt und wurdest zu deiner Sicherheit zum Spawn teleportiert!");
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> player.teleport(new Location(Bukkit.getWorld("world"), 0.50, 280, 0.50)), 1L);
            player.clearTitle();
            player.sendTitle(ChatColor.GOLD.toString() + ChatColor.BOLD + "⚡ Warps ⚡", ChatColor.GOLD + "Du befindest dich nun bei: " + ChatColor.GREEN + "Spawn");
        }
    }

    public int getPlaytime(Player player) {
        int playtime = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20;
        int playtimeInHours = Math.round(playtime / 3600);
        return playtimeInHours;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(inCombat.contains(player)) {
            player.setHealth(0);
            player.kickPlayer(ChatColor.RED + "Du hast den Server im Combat verlassen!");
            event.setQuitMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + ChatColor.BOLD + "Combat".toUpperCase() + ChatColor.RESET + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " " + player.getName());
            return;
        }
        event.setQuitMessage(Main.getPrefix("-") + player.getName());
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent event) {
        for (Enchantment enchantment : event.getEnchantsToAdd().keySet()) {
            event.getItem().addEnchantment(enchantment, enchantment.getMaxLevel());
        }
        event.setExpLevelCost(3);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
        event.setFormat(player.getDisplayName() + ChatColor.BOLD + ChatColor.DARK_GRAY + " >> " + ChatColor.RESET + ChatColor.WHITE + event.getMessage());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        player.sendTitle(ChatColor.RED + "Du bist gestorben!", ChatColor.GOLD + "Du befindest dich nun bei: " + ChatColor.AQUA + "Spawn");
        ExperienceOrb orb = player.getWorld().spawn(player.getLocation(), ExperienceOrb.class);
        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + ChatColor.BOLD + StringFormatUtil.capitalize(player.getLastDamageCause().getCause().toString()) + ChatColor.RESET + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " " + player.getName());
        orb.setExperience(orb.getExperience() + player.getTotalExperience());
        for(Player allPlayer : Bukkit.getOnlinePlayers()) {
            allPlayer.spawnParticle(Particle.LAVA, player.getLocation(), 50);
        }

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 280, 0.5));
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, 100, 1);
            player.setExp(0);
            player.setLevel(0);
        }, 1L);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player player) {
            if(event.getEntity().getLocation().distance(new Location(event.getEntity().getWorld(), 0, 280, 0)) <= 50) event.setCancelled(true);

            if(event.getCause().equals(EntityDamageEvent.DamageCause.FLY_INTO_WALL)) {
                event.setCancelled(true);
                return;
            }
            player.sendActionBar(ChatColor.GOLD + "Du hast durch " + ChatColor.GREEN + event.getCause() + " " + ChatColor.AQUA + Math.round(event.getFinalDamage()) + " HP" + ChatColor.GOLD + " Schaden genommen!");
        }
    }

    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        if(event.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK)) {
            Bukkit.broadcastMessage(ChatColor.GRAY + event.getPlayer().getName() + " schläft! zZz");
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player player && event.getDamager() instanceof Player damager && event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {

            inCombat.add(player);
            inCombat.add(damager);

            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                inCombat.remove(player);
                inCombat.remove(damager);
            }, 15*20L);
        }
    }

    @EventHandler
    public void onExp(PlayerPickupExperienceEvent event) {
        Player player = event.getPlayer();
        int xp = event.getExperienceOrb().getExperience();
        player.sendMessage(ChatColor.GOLD + "Du hast " + ChatColor.AQUA + xp + " XP" + ChatColor.GOLD + " bekommen!");
    }

    @EventHandler
    public void onBoost(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if(player.isGliding() && player.isOp()) {
            event.setCancelled(true);
            player.boostElytra(new ItemBuilder(Material.FIREWORK_ROCKET).build());
            return;
        }

        if(player.isGliding() && player.getInventory().contains(Material.FIREWORK_ROCKET)) {
            event.setCancelled(true);
            player.boostElytra(new ItemBuilder(Material.FIREWORK_ROCKET).build());
            remove(player.getInventory(), Material.FIREWORK_ROCKET, 1);
            return;
        }

        if(player.isGliding() && player.getEnderChest().contains(Material.FIREWORK_ROCKET)) {
            event.setCancelled(true);
            player.boostElytra(new ItemBuilder(Material.FIREWORK_ROCKET).build());
            remove(player.getEnderChest(), Material.FIREWORK_ROCKET, 1);
            return;
        }

        for(ItemStack stack : player.getInventory().getStorageContents()) {

        }
    }

    public void remove(Inventory inv, Material mat, int amount) {
        for(int i = 0; i < inv.getSize(); i++){
            ItemStack stack = inv.getItem(i);
            if(stack != null && stack.getType().equals(mat)) {
                int value = stack.getAmount() - amount;
                stack.setAmount(value);
                inv.setItem(i, value > 0 ? stack : null);
                break;
            }
        }
    }

    @EventHandler
    public void onCreeperExplode(EntityExplodeEvent event) {
        if(event.getEntity().getType().equals(EntityType.CREEPER) || event.getEntity().getType().equals(EntityType.ENDER_CRYSTAL) || event.getEntityType().equals(EntityType.MINECART_TNT)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(event.getBlock().getLocation().distance(new Location(event.getBlock().getWorld(), 0, 280, 0)) <= 50 && !player.getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true) ;
            return;
        }

        if(event.getBlock().getType().equals(Material.SPAWNER) && player.getItemInHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
            CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();
            EntityType type = spawner.getSpawnedType();
            ItemStack stack = new ItemStack(Material.SPAWNER);
            stack.getItemMeta().setDisplayName(ChatColor.DARK_PURPLE + type.getName().toUpperCase() + " SPAWNER");
            event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), stack);
        }
        ExperienceOrb orb = event.getPlayer().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class);
        orb.setExperience(orb.getExperience() + 1);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(event.getBlock().getLocation().distance(new Location(event.getBlock().getWorld(), 0, 280, 0)) <= 50 && !event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) event.setCancelled(true);
    }

    @EventHandler
    public void onFishing(PlayerFishEvent event) {
        event.setExpToDrop(event.getPlayer().getLevel());
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if(event.getEntity().getType().equals(EntityType.VEX)) event.setCancelled(true);
        if(event.getEntity().getLocation().distance(new Location(event.getEntity().getWorld(), 0, 280, 0)) <= 50 && (event.getEntityType().equals(EntityType.CREEPER) || event.getEntityType().equals(EntityType.ZOMBIE) || event.getEntityType().equals(EntityType.SKELETON) || event.getEntityType().equals(EntityType.SPIDER))) event.setCancelled(true);
    }

    @EventHandler
    public void onRaid(RaidSpawnWaveEvent event) {
        for(Raider raider : event.getRaiders()) {
            raider.getWorld().spawn(raider.getLocation(), Pillager.class);
            raider.getWorld().spawn(raider.getLocation(), Vindicator.class);
            raider.getWorld().spawn(raider.getLocation(), Evoker.class);
        }
    }
}
