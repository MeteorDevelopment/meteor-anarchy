package minegame159.meteoranarchy;

import minegame159.meteoranarchy.commands.Commands;
import minegame159.meteoranarchy.listeners.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class MeteorAnarchy extends JavaPlugin {
    public static MeteorAnarchy INSTANCE;

    public static final String PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.RED + "MA" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;

    @Override
    public void onEnable() {
        INSTANCE = this;

        Listeners.init();
        Perms.init();
        Commands.init();
        Nicks.init();
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);

        Nicks.save();
    }
}
