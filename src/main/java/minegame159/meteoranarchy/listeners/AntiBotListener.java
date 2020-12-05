package minegame159.meteoranarchy.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;

public class AntiBotListener implements Listener {
    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        byte[] ip = event.getPlayer().getAddress().getAddress().getAddress();
        int timesConnectedWithTheSameIp = 0;

        for (Player player : Bukkit.getOnlinePlayers()) {
            byte[] ip2 = player.getAddress().getAddress().getAddress();

            if (Arrays.equals(ip, ip2)) {
                timesConnectedWithTheSameIp++;

                if (timesConnectedWithTheSameIp >= 3) {
                    event.getPlayer().kickPlayer("You are not allowed to log in with more than 3 accounts from the same IP.");
                    break;
                }
            }
        }
    }
}
