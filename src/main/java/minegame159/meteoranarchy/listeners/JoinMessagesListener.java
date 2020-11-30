package minegame159.meteoranarchy.listeners;

import minegame159.meteoranarchy.Nicks;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinMessagesListener implements Listener {
    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', Nicks.get(event.getPlayer())) + ChatColor.GRAY + " joined");
    }

    @EventHandler
    private void onPlayerQuite(PlayerQuitEvent event) {
        event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', Nicks.get(event.getPlayer())) + ChatColor.GRAY + " left");
    }
}
