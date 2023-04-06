package net.onfyre.onfyresmp.commands.carry;

import net.onfyre.onfyresmp.utils.ServerLagController;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class PerformanceCommand implements CommandExecutor {

    private final OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        int freeMemory = (int) (Runtime.getRuntime().freeMemory()/1024/1024);
        int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024/1024);
        sender.sendMessage("");
        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + ChatColor.BOLD + "⚡" + ChatColor.RESET + ChatColor.DARK_GRAY + "] " + ChatColor.GOLD + ChatColor.BOLD + "Runtime Performance Overview");
        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + ChatColor.BOLD + "⚡" + ChatColor.RESET + ChatColor.DARK_GRAY + "] " + ChatColor.GOLD + "TPS: ");
        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + ChatColor.BOLD + "⚡" + ChatColor.RESET + ChatColor.DARK_GRAY + "] " + ChatColor.GREEN + getTPS() + ChatColor.GRAY + "TPS - " + getTickPercentage() + ChatColor.GRAY + "%");
        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + ChatColor.BOLD + "⚡" + ChatColor.RESET + ChatColor.DARK_GRAY + "] ");
        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + ChatColor.BOLD + "⚡" + ChatColor.RESET + ChatColor.DARK_GRAY + "] " + ChatColor.GOLD + "RAM-Usage: ");
        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + ChatColor.BOLD + "⚡" + ChatColor.RESET + ChatColor.DARK_GRAY + "] " + ChatColor.GREEN + freeMemory + ChatColor.GRAY + "MB von " + ChatColor.GREEN + maxMemory + ChatColor.GRAY + "MB ");
        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + ChatColor.BOLD + "⚡" + ChatColor.RESET + ChatColor.DARK_GRAY + "] ");
        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + ChatColor.BOLD + "⚡" + ChatColor.RESET + ChatColor.DARK_GRAY + "] " + ChatColor.GOLD + "Prozessor-Usage:");
        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + ChatColor.BOLD + "⚡" + ChatColor.RESET + ChatColor.DARK_GRAY + "] " + ChatColor.GREEN + getAverageSystemLoad() + "%" + ChatColor.GRAY + " @ " + ChatColor.GREEN + Runtime.getRuntime().availableProcessors() + ChatColor.GRAY + " Cores");
        return false;
    }

    public String getTPS() {
        float ticksPerSecond = ServerLagController.getTPS();
        if(ticksPerSecond >= 18.0) {
            return ChatColor.GREEN.toString() + ticksPerSecond;
        } else if(ticksPerSecond >= 16.0) {
            return ChatColor.YELLOW.toString() + ticksPerSecond;
        } else if(ticksPerSecond >= 14.0) {
            return ChatColor.RED.toString() + ticksPerSecond;
        } else return ChatColor.DARK_RED.toString() + ticksPerSecond;
    }

    public String getTickPercentage() {
        float percentage = ServerLagController.getTPS()*5;
        if(percentage >= 18.0*5) {
            return ChatColor.GREEN.toString() + percentage;
        } else if(percentage >= 16.0*5) {
            return ChatColor.YELLOW.toString() + percentage;
        } else if(percentage >= 14.0*5) {
            return ChatColor.RED.toString() + percentage;
        } else return ChatColor.DARK_RED.toString() + percentage;
    }

    public String getAverageSystemLoad() {
        double load = osBean.getSystemLoadAverage();
        if(load <= 25.0) {
            return ChatColor.GREEN.toString() + load;
        } else if(load <= 50.0) {
            return ChatColor.YELLOW.toString() + load;
        } else if(load <= 75.0) {
            return ChatColor.RED.toString() + load;
        } else return ChatColor.DARK_RED.toString() + load;
    }
}
