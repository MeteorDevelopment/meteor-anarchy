package minegame159.meteoranarchy.listeners;

import minegame159.meteoranarchy.MeteorAnarchy;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.reflections.Reflections;

public class Listeners {
    public static void init() {
        for (Class<? extends Listener> klass : new Reflections("minegame159.meteoranarchy.listeners").getSubTypesOf(Listener.class)) {
            try {
                Listener listener = klass.newInstance();
                Bukkit.getPluginManager().registerEvents(listener, MeteorAnarchy.INSTANCE);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
