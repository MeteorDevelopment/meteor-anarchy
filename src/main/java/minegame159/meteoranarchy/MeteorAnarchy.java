package minegame159.meteoranarchy;

import minegame159.meteoranarchy.commands.Commands;
import minegame159.meteoranarchy.listeners.Listeners;
import minegame159.meteoranarchy.users.Users;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public final class MeteorAnarchy extends JavaPlugin {
    public static MeteorAnarchy INSTANCE;

    public static final String PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.RED + "MA" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;

    @Override
    public void onEnable() {
        INSTANCE = this;

        Listeners.init();
        Perms.init();
        Commands.init();

        Nicks.load();
        Users.INSTANCE.load();

        Discord.start();

        long delay = 60 * 60 * 20;
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            Bukkit.broadcastMessage(ChatColor.RED + ChatColor.BOLD.toString() + " -- " + ChatColor.RESET + ChatColor.GOLD + "Don't forget to /vote" + ChatColor.RED + ChatColor.BOLD.toString() + " --");
            Bukkit.broadcastMessage(ChatColor.RED + ChatColor.BOLD.toString() + " -- " + ChatColor.RESET + ChatColor.GOLD + "To keep the server alive purchase a /rank" + ChatColor.RED + ChatColor.BOLD.toString() + " --");
        }, delay, delay);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);

        Nicks.save();
        Users.INSTANCE.save();

        Discord.stop();
    }
}
