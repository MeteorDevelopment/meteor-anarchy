package minegame159.meteoranarchy.listeners;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import it.unimi.dsi.fastutil.objects.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

public class AntiCheatListener implements Listener {
    public final Object2IntMap<Player> ignoreTicks = new Object2IntOpenHashMap<>();
    private final Object2ObjectMap<Player, Location> lastValidSpeedPositions = new Object2ObjectOpenHashMap<>();
    private final Object2ObjectMap<Player, Location> lastOnGroundPositions = new Object2ObjectOpenHashMap<>();
    private final Object2IntMap<Player> inAirTicks = new Object2IntOpenHashMap<>();
    private final Object2DoubleMap<Player> lastVelocityY = new Object2DoubleOpenHashMap<>();
    private final Object2IntMap<Player> highYVelocityTicks = new Object2IntOpenHashMap<>();
    private final Object2IntMap<Player> highButLessYVelocityTicks = new Object2IntOpenHashMap<>();
    private final Object2ObjectMap<Player, Location> lastValidPhasePositions = new Object2ObjectOpenHashMap<>();

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.isCancelled() || player.isDead() || player.getGameMode() != GameMode.SURVIVAL) return;

        Location from = event.getFrom();
        Location to = event.getTo();

        if (!ignore(player)) {
            double y = 0;
            double velY = to.getY() - from.getY();
            if (velY > 2) y = Math.pow(velY, 2);
            double speed = Math.sqrt(Math.pow(to.getX() - from.getX(), 2) + y + Math.pow(to.getZ() - from.getZ(), 2));

            if (velY > 0.5 ) {
                int ticks = highYVelocityTicks.getInt(player);
                highYVelocityTicks.put(player, ticks + 1);
            } else {
                highYVelocityTicks.removeInt(player);
            }

            if (velY > 0.2 && !player.isGliding()) {
                int ticks = highButLessYVelocityTicks.getInt(player);
                highButLessYVelocityTicks.put(player, ticks + 1);
            } else {
                highButLessYVelocityTicks.removeInt(player);
            }

            double maxSpeed = 0.85; // 16 BPS
            if (player.isGliding()) maxSpeed *= 2 + 0.10625; // 34 BPS

            if (speed > maxSpeed) {
                Location pos = lastValidSpeedPositions.get(player);
                if (pos != null) {
                    event.setTo(pos);
                    to = pos;
                }
            } else {
                lastValidSpeedPositions.put(player, event.getTo());
            }
        } else {
            highYVelocityTicks.removeInt(player);
            highButLessYVelocityTicks.removeInt(player);
            lastValidSpeedPositions.put(player, event.getTo());
        }

        lastVelocityY.put(player, to.getY() - from.getY());
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        lastValidSpeedPositions.remove(player);
        ignoreTicks.removeInt(player);
        lastOnGroundPositions.remove(player);
        inAirTicks.removeInt(player);
        lastVelocityY.removeDouble(player);
        highYVelocityTicks.removeInt(player);
        highButLessYVelocityTicks.removeInt(player);
        lastValidPhasePositions.remove(player);
    }

    @EventHandler
    private void onPlayerVelocity(PlayerVelocityEvent event) {
        ignoreTicks.put(event.getPlayer(), (int) Math.round(Math.max(event.getVelocity().length() * 25, 60)));
    }

    @EventHandler
    private void onServerTickEnd(ServerTickEndEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.isDead() || player.getGameMode() != GameMode.SURVIVAL) continue;

            boolean onGround = isOnGround(player);
            if (onGround) {
                lastOnGroundPositions.put(player, player.getLocation());
            }

            if (ignore(player) || player.isGliding()) continue;

            double velY = lastVelocityY.getOrDefault(player, 0.0);
            int yVelTicks = highYVelocityTicks.getInt(player);
            int lessYVelTicks = highButLessYVelocityTicks.getInt(player);

            boolean inAir = !onGround && velY >= 0 && (velY <= 0.25 || yVelTicks > 4 || lessYVelTicks > 8);
            int ticksInAir = 0;

            if (inAir) {
                ticksInAir = inAirTicks.getInt(player);
                inAirTicks.put(player, ticksInAir + 1);
            } else {
                inAirTicks.removeInt(player);
            }

            if (ticksInAir >= 10) {
                Location pos = lastOnGroundPositions.get(player);
                if (pos != null) player.teleport(pos);
            }

            // Phase
            if (isInBlock(player)) {
                Location pos = lastValidPhasePositions.get(player);
                if (pos != null) player.teleport(pos);
            } else {
                lastValidPhasePositions.put(player, player.getLocation());
            }
        }
    }

    private boolean isInBlock(Player player) {
        return player.getLocation().getBlock().getType().isOccluding();
    }

    private boolean isOnGround(Player player) {
        Location pos = player.getLocation();

        for (int y = 0; y >= -1; y--) {
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    Block block = pos.add(x, y, z).getBlock();

                    if (!block.isPassable() || isClimbable(block) || block.isLiquid()) return true;
                    pos.subtract(x, y, z);
                }
            }
        }

        return false;
    }

    private boolean isClimbable(Block block) {
        Material m = block.getType();
        return m == Material.LADDER || m == Material.VINE || m == Material.TWISTING_VINES_PLANT || m == Material.WEEPING_VINES_PLANT;
    }

    private boolean ignore(Player player) {
        if (ignoreTicks.containsKey(player)) {
            int ticksIgnore = ignoreTicks.getInt(player);

            if (ticksIgnore <= 0) {
                ignoreTicks.removeInt(player);
            } else {
                ignoreTicks.put(player, ticksIgnore - 1);
                return true;
            }
        }

        return false;
    }
}
