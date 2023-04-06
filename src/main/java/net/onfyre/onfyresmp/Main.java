package net.onfyre.onfyresmp;

import net.onfyre.onfyresmp.commands.*;
import net.onfyre.onfyresmp.commands.admin.*;
import net.onfyre.onfyresmp.commands.carry.*;
import net.onfyre.onfyresmp.listeners.InteractListener;
import net.onfyre.onfyresmp.listeners.InventoryClickListener;
import net.onfyre.onfyresmp.listeners.MainListener;
import net.onfyre.onfyresmp.ui.MainScoreBoard;
import net.onfyre.onfyresmp.ui.Tablist;
import net.onfyre.onfyresmp.utils.BalanceUtil;
import net.onfyre.onfyresmp.utils.Config;
import net.onfyre.onfyresmp.utils.CountdownTimer;
import net.onfyre.onfyresmp.utils.ServerLagController;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;

public final class Main extends JavaPlugin {

    public static Main instance;
    public static Config statsConfig;
    public static boolean isBlocked;

    @Override
    public void onLoad() {
        Bukkit.getLogger().log(Level.INFO, getPrefix() + "Starte Plugin...");
    }

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getLogger().log(Level.INFO, getPrefix() + "Plugin wurde gestartet!");
        try {
            Bukkit.getLogger().log(Level.INFO, getPrefix() + "Registriere Listener...");
            listenerRegistration();
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, getPrefix() + "Listener Registrierung fehlgeschlagen!");
        }
        try {
            Bukkit.getLogger().log(Level.INFO, getPrefix() + "Registriere Commands...");
            commandRegistration();
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, getPrefix() + "Command Registrierung fehlgeschlagen!");
        }
        try {
            Bukkit.getLogger().log(Level.INFO, getPrefix() + "Registriere Synchronisierte Aufgaben...");
            Bukkit.getScheduler().scheduleSyncRepeatingTask(getInstance(), this::updateTablist, 1L, 20L);
            Bukkit.getScheduler().scheduleSyncRepeatingTask(getInstance(), this::inCombatActionBar, 10L, 1L);
            Bukkit.getScheduler().scheduleSyncRepeatingTask(getInstance(), this::updateScoreboard, 1L, 20L);
            Bukkit.getScheduler().scheduleSyncRepeatingTask(getInstance(), this::autoServerRestart, 2*3600*20L, 2*3600*20L);
            Bukkit.getScheduler().scheduleSyncRepeatingTask(getInstance(), this::checkForItems, 1L, 10L);
            Bukkit.getScheduler().scheduleSyncRepeatingTask(getInstance(), this::checkForUpdates, 1L, 10L);
            Bukkit.getScheduler().scheduleSyncRepeatingTask(getInstance(), new ServerLagController(), 1L, 1L);
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getLogger().log(Level.SEVERE, getPrefix() + "Synchronisierte Aufgaben Registrierung fehlgeschlagen!");
        }
        try {
            Bukkit.getLogger().log(Level.INFO, getPrefix() + "Variablen werden gesetzt...");
            instance = this;
            statsConfig = new Config("stats.yml", getDataFolder());
            Bukkit.getWorld("world").setSpawnLocation(0, 280, 0);
            Location from = new Location(Bukkit.getWorld("mines"), -10, -58, 3);
            Location to = new Location(Bukkit.getWorld("mines"), -16, -60, -3);

        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, getPrefix() + "Variablensetzung fehlgeschlagen!");
            e.printStackTrace();
        }
        Bukkit.getLogger().log(Level.INFO, getPrefix() + "Fertig!");
    }

    public void listenerRegistration() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new MainListener(), this);
        pm.registerEvents(new InventoryClickListener(), this);
        pm.registerEvents(new InteractListener(), this);
    }

    public void commandRegistration() {
        getCommand("craft").setExecutor(new CraftCommand());
        getCommand("ec").setExecutor(new EnderchestCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("latency").setExecutor(new PingCommand());
        getCommand("ecsee").setExecutor(new EcseeCommand());
        getCommand("invsee").setExecutor(new InvseeCommand());
        getCommand("stash").setExecutor(new StashCommand());
        getCommand("acp").setExecutor(new AcpCommand());
        getCommand("server").setExecutor(new PerformanceCommand());
        getCommand("performance").setExecutor(new PerformanceCommand());
        getCommand("ban").setExecutor(new BanCommand());
        getCommand("unban").setExecutor(new UnbanCommand());
        getCommand("kick").setExecutor(new KickCommand());
        getCommand("keys").setExecutor(new KeysCommand());
        getCommand("stats").setExecutor(new StatsCommand());
        getCommand("s").setExecutor(new StatsCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("flyspeed").setExecutor(new FlySpeedCommand());
        getCommand("tpa").setExecutor(new TPACommand());
        getCommand("tpaccept").setExecutor(new TPAcceptCommand());
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().log(Level.WARNING, getPrefix() + "Plugin wurde gestoppt!");
        Bukkit.getLogger().log(Level.WARNING, getPrefix() + "Der Stash wurde geleert!");
    }

    public void updateScoreboard() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            MainScoreBoard.updateScoreBoard(player);
        }
    }

    public void inCombatActionBar() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(MainListener.inCombat.contains(player)) {
                player.sendActionBar(ChatColor.RED + "!!! DU BEFINDEST DICH IM COMBAT !!!");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
            }
        }
    }

    public void autoServerRestart() {
        if(isBlocked) return;
        CountdownTimer timer = new CountdownTimer(getInstance(),30, () -> {
            for(Player allPlayer : Bukkit.getOnlinePlayers()) {
                allPlayer.playSound(allPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
                allPlayer.kickPlayer(Main.getPrefix() + "Der Server wurde neugestartet! " + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Scheuduled" + ChatColor.DARK_GRAY + "]");
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
        }, (t) -> {
            Bukkit.broadcastMessage(Main.getPrefix() + "Der Server wird in " + ChatColor.GREEN + t.getSecondsLeft() + " Sekunden" + ChatColor.GOLD + " automatisch neugestartet!");
            for(Player allPlayer : Bukkit.getOnlinePlayers()) {
                allPlayer.playSound(allPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
            }
        });
        timer.scheduleTimer();
    }

    public void updateTablist() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            new Tablist(player, player.getServer());
        }
    }

    public void checkForItems() {
        for(Player allPlayer : Bukkit.getOnlinePlayers()) {
            int currentCurrencyValue = BalanceUtil.countCurrency(allPlayer);
            statsConfig.set(allPlayer.getUniqueId() + ".balance", currentCurrencyValue);
            statsConfig.set(allPlayer.getUniqueId() + ".playtime", getPlaytime(allPlayer));
            statsConfig.set(allPlayer.getUniqueId() + ".deaths", allPlayer.getStatistic(Statistic.DEATHS));
            statsConfig.set(allPlayer.getUniqueId() + ".level", allPlayer.getLevel());
            statsConfig.save();
        }
    }

    public void checkForUpdates() {
        try {
            URL url = new URL("");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        } catch (Exception e) {
            Bukkit.broadcastMessage(Main.getPrefix() + "Failed to check for updates");
        }
    }

    public int getPlaytime(Player player) {
        int playtime = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20;
        int playtimeInHours = Math.round(playtime / 3600);
        return playtimeInHours;
    }

    public static String getPrefix() {
        String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + ChatColor.BOLD + "OnFyreSMP" + ChatColor.RESET + ChatColor.DARK_GRAY + "]" + ChatColor.GOLD + " ";
        return prefix.toString();
    }

    public static String getPrefix(String name) {
        String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + ChatColor.BOLD + name + ChatColor.RESET + ChatColor.DARK_GRAY + "]" + ChatColor.GOLD + " ";
        return prefix.toString();
    }

    public static JavaPlugin getInstance() {
        return instance;
    }
}
