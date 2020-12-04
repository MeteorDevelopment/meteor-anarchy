package minegame159.meteoranarchy.listeners;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import minegame159.meteoranarchy.MeteorAnarchy;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

public class SnowballDespawnListener implements Listener {
    private final Object2IntMap<Snowball> snowballs = new Object2IntOpenHashMap<>();

    @EventHandler
    private void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin() == MeteorAnarchy.INSTANCE) snowballs.clear();
    }

    @EventHandler
    private void onEntityAddToWorld(EntityAddToWorldEvent event) {
        if (event.getEntity() instanceof Snowball) snowballs.put((Snowball) event.getEntity(), 0);
    }

    @EventHandler
    private void onServerTickEnd(ServerTickEndEvent event) {
        for (Snowball snowball : snowballs.keySet()) {
            int ticks = snowballs.getInt(snowball) + 1;
            snowballs.put(snowball, ticks);

            if (ticks > 10 * 20) snowball.remove();
        }
    }

    @EventHandler
    private void on(EntityRemoveFromWorldEvent event) {
        if (event.getEntity() instanceof Snowball) snowballs.removeInt(event.getEntity());
    }
}
