package minegame159.meteoranarchy.listeners;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import email.com.gmail.cosmoconsole.bukkit.deathmsg.DeathMessageBroadcastEvent;
import minegame159.meteoranarchy.Nicks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class DeathMessageListener implements Listener {
    private final Map<Player, EntityDamageEvent.DamageCause> lastDmgCause = new HashMap<>();
    private final Map<Player, Entity> lastAttacker = new HashMap<>();
    private final Map<EnderCrystal, Player> crystalExploder = new HashMap<>();
    
    @EventHandler
    private void onTick(ServerTickEndEvent event) {
        crystalExploder.clear();
    }

    @EventHandler
    private void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        lastDmgCause.put(player, event.getCause());
    }

    @EventHandler
    private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof EnderCrystal && event.getDamager() instanceof Player) {
            crystalExploder.put((EnderCrystal) event.getEntity(), (Player) event.getDamager());
        }

        // Damaging players
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        lastDmgCause.put(player, event.getCause());
        lastAttacker.put(player, event.getDamager());
    }
    
    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        lastDmgCause.remove(event.getPlayer());
        lastAttacker.remove(event.getPlayer());
    }

    @EventHandler
    private void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        EntityDamageEvent.DamageCause cause = lastDmgCause.get(player);
        Entity attacker = lastAttacker.get(player);

        // Killed by entity explosion
        if (cause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            Entity exploder = null;
            String with = null;

            if (attacker instanceof EnderCrystal) {
                exploder = crystalExploder.remove(attacker);
                with = ChatColor.GREEN + "End Crystal";
            }

            if (exploder != null) {
                String exploderName = exploder instanceof Player ? ChatColor.translateAlternateColorCodes('&', Nicks.get((Player) exploder)) : exploder.getName();
                Bukkit.broadcastMessage(String.format("%s%s %swas nuked by %s%s %swith %s", ChatColor.GREEN, ChatColor.translateAlternateColorCodes('&', Nicks.get(player)), ChatColor.RED, ChatColor.GREEN, exploderName, ChatColor.RED, with));
            }
        }
    }

    @EventHandler
    private void onDeathMessageBroadcastMessage(DeathMessageBroadcastEvent event) {
        if (event.getMessage().toPlainText().endsWith("meteoranarchy remove broadcast")) event.setCancelled(true);
    }
}
