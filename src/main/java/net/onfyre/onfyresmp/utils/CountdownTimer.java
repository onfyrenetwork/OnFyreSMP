package net.onfyre.onfyresmp.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class CountdownTimer implements Runnable {
    private JavaPlugin plugin;
    private Integer assignedTaskId;

    private int seconds;
    private int secondsLeft;

    private Consumer<CountdownTimer> everySecond;
    private Runnable afterTimer;
    private ArrayList<Integer> acceptedSeconds = new ArrayList<>(List.of(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 20, 25, 30, 60, 120}));

    public CountdownTimer(JavaPlugin plugin, Integer seconds, Runnable afterTimer, Consumer<CountdownTimer> everySecond) {

        this.plugin = plugin;

        this.seconds = seconds;
        this.secondsLeft = seconds;

        this.afterTimer = afterTimer;
        this.everySecond = everySecond;
    }

    @Override
    public void run() {
        if (secondsLeft < 1) {
            afterTimer.run();
            if (assignedTaskId != null) Bukkit.getScheduler().cancelTask(assignedTaskId);
            return;
        }

        if(acceptedSeconds.contains(secondsLeft)) {
            everySecond.accept(this);
        }
        secondsLeft--;
    }

    public int getTotalSeconds() {
        return seconds;
    }

    public int getSecondsLeft() {
        return secondsLeft;
    }

    public void scheduleTimer() {
        this.assignedTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 0L, 20L);
    }
}