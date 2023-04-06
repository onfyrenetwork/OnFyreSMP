package net.onfyre.onfyresmp.ui;

import net.onfyre.onfyresmp.Main;
import net.onfyre.onfyresmp.utils.BalanceUtil;
import net.onfyre.onfyresmp.utils.StringFormatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.UUID;

public class MainScoreBoard {

    private static final HashMap<UUID, Scoreboard> scoreboardMap = new HashMap<>();

    public MainScoreBoard() {

    }

    public static void setScoreBoard(Player player) {
        if (!scoreboardMap.containsKey(player.getUniqueId())) {
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective playerobjective = scoreboard.registerNewObjective(player.getUniqueId().toString(), "dummy", ChatColor.GOLD.toString() + ChatColor.BOLD + "OnFyreSMP");
            playerobjective.setDisplaySlot(DisplaySlot.SIDEBAR);
            scoreboardMap.put(player.getUniqueId(), scoreboard);
        }
        player.setScoreboard(scoreboardMap.get(player.getUniqueId()));
    }

    public static void updateScoreBoard(Player player) {
        if(scoreboardMap.get(player.getUniqueId()) == null) {
            return;
        }
        Scoreboard playerScoreBoard = scoreboardMap.get(player.getUniqueId());
        Objective playerScoreBoardObjective = playerScoreBoard.getObjective(player.getUniqueId().toString());

        if (playerScoreBoardObjective == null) {
            return;
        }

        for (String entry : playerScoreBoard.getEntries()) {
            Score score = playerScoreBoardObjective.getScore(entry);
            score.resetScore();
        }

        setScore(player, ChatColor.DARK_PURPLE.toString(), 15);
        setScore(player, ChatColor.GRAY.toString() + ChatColor.BOLD + "Kontostand >", 14);
        setScore(player, ChatColor.GOLD.toString() + BalanceUtil.countCurrency(player) + " Diamanten", 13);
        setScore(player, ChatColor.BLUE.toString(), 12);
        setScore(player, ChatColor.GRAY.toString() + ChatColor.BOLD + "Tode >", 11);
        setScore(player, ChatColor.GOLD.toString() + Main.statsConfig.get(Bukkit.getOfflinePlayer(player.getName()).getUniqueId() + ".deaths"), 10);
        setScore(player, ChatColor.BLACK.toString(), 9);
        setScore(player, ChatColor.GRAY.toString() + ChatColor.BOLD + "Playtime >", 8);
        setScore(player, ChatColor.GOLD.toString() + Main.statsConfig.get(Bukkit.getOfflinePlayer(player.getName()).getUniqueId() + ".playtime"), 7);
        setScore(player, ChatColor.WHITE.toString(), 6);
        setScore(player, ChatColor.GRAY.toString() + ChatColor.BOLD + "Ping >", 5);
        setScore(player, ChatColor.GOLD.toString() + player.getPing() + "ms", 4);
        setScore(player, ChatColor.DARK_AQUA.toString(), 3);
        setScore(player, ChatColor.GRAY.toString() + ChatColor.BOLD + "Discord >", 2);
        setScore(player, ChatColor.GOLD + "discord.io/onfyre", 1);
    }

    public static void setScore(Player player, String text, int scoreValue) {
        Objective playerObjektiv = scoreboardMap.get(player.getUniqueId()).getObjective(player.getUniqueId().toString());

        if (playerObjektiv == null) {
            return;
        }

        Score score = playerObjektiv.getScore(text);
        score.setScore(scoreValue);
    }
}