package minegame159.meteoranarchy.utils;

import org.bukkit.Material;
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

    public static boolean isShulkerBox(ItemStack item) {
        Material type = item.getType();
        return type == Material.SHULKER_BOX || type == Material.WHITE_SHULKER_BOX || type == Material.ORANGE_SHULKER_BOX || type == Material.MAGENTA_SHULKER_BOX || type == Material.LIGHT_BLUE_SHULKER_BOX || type == Material.YELLOW_SHULKER_BOX || type == Material.LIME_SHULKER_BOX || type == Material.PINK_SHULKER_BOX || type == Material.GRAY_SHULKER_BOX || type == Material.LIGHT_GRAY_SHULKER_BOX || type == Material.CYAN_SHULKER_BOX || type == Material.PURPLE_SHULKER_BOX || type == Material.BLUE_SHULKER_BOX || type == Material.BROWN_SHULKER_BOX || type == Material.GREEN_SHULKER_BOX || type == Material.RED_SHULKER_BOX || type == Material.BLACK_SHULKER_BOX;
    }
}
