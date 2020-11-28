package minegame159.meteoranarchy;

import minegame159.meteoranarchy.commands.Commands;
import minegame159.meteoranarchy.listeners.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MeteorAnarchy extends JavaPlugin {
    public static MeteorAnarchy INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        Listeners.init();
        Commands.init();
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }
}
