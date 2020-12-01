package minegame159.meteoranarchy.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.UUID;

public class Utils {
    public static void giveItem(Player player, ItemStack item) {
        Map<Integer, ItemStack> map = player.getInventory().addItem(item);

        for (Integer i : map.keySet()) {
            player.getWorld().dropItemNaturally(player.getLocation(), map.get(i));
        }
    }

    public static String generateToken() {
        UUID id = UUID.randomUUID();
        long hi = id.getMostSignificantBits();
        long lo = id.getLeastSignificantBits();
        byte[] bytes = ByteBuffer.allocate(16).putLong(hi).putLong(lo).array();
        BigInteger big = new BigInteger(bytes);
        return big.toString().replace('-','1');
    }
}
