package minegame159.meteoranarchy.listeners;

import minegame159.meteoranarchy.Perms;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class GreentextListener implements Listener {
    @EventHandler
    private void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (event.getMessage().startsWith(">") && event.getPlayer().hasPermission(Perms.GREENTEXT)) {
            event.setMessage(ChatColor.GREEN + event.getMessage());
        }
    }
}
