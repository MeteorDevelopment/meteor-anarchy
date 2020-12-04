package minegame159.meteoranarchy.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

public class BookUpdateListener implements Listener {
    @EventHandler
    private void onBookEdit(PlayerEditBookEvent event) {
        BookMeta meta = event.getNewBookMeta();

        // Check title length
        if (meta.hasTitle() && meta.getTitle().length() > 30) {
            event.setCancelled(true);
            return;
        }

        // Check page count
        if (meta.getPages().size() > 50) {
            event.setCancelled(true);
            return;
        }

        for (String page : meta.getPages()) {
            if (page.length() > 2000) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
