package minegame159.meteoranarchy.listeners;

import minegame159.meteoranarchy.Ignores;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class IgnoreListener implements Listener {
    @EventHandler
    private void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();

        event.getRecipients().removeIf(player -> Ignores.hasReceiverIgnored(sender, player));
    }
}
