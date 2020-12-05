package minegame159.meteoranarchy.users;

import com.google.common.collect.Lists;
import minegame159.meteoranarchy.Discord;
import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.Nicks;
import minegame159.meteoranarchy.utils.NBT;
import minegame159.meteoranarchy.utils.Utils;
import net.querz.nbt.tag.CompoundTag;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class User {
    public UUID uuid;

    public int totalVotes;
    public int offlineVotes;

    public long rankExpiresAt;
    public boolean hadRank;

    public boolean hasDiscord;
    public String discordId = "";

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public void addBenefits(Player player) {
        if (rankExpiresAt == 0) {
            rankExpiresAt = System.currentTimeMillis();

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent add rank");
            if (hasDiscord) Discord.addRankRole(discordId);
        }

        if (!hadRank) {
            ItemStack item = new ItemStack(Material.POPPY);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.GOLD + "Golden Poppy");
            meta.setLore(Lists.newArrayList(ChatColor.RED + "Thanks for keeping the server alive " + player.getName()));

            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            item.setItemMeta(meta);
            Utils.giveItem(player, item);

            hadRank = true;
        }

        player.sendMessage(MeteorAnarchy.PREFIX + ChatColor.GOLD + "Thanks for supporting the server");
        player.sendMessage(MeteorAnarchy.PREFIX + "Do " + ChatColor.WHITE + "/rank" + ChatColor.GRAY + " to get list of benefits");

        if (!hasDiscord) {
            player.sendMessage(MeteorAnarchy.PREFIX + ChatColor.RED + "WARNING: " + ChatColor.GRAY + "You haven't linked your discord account, do it using " + ChatColor.WHITE + "/discord");
        }
    }

    public void removeBenefits(UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + offlinePlayer.getName() + " parent remove rank");

        if (hasDiscord) Discord.removeRankRole(discordId);

        Nicks.reset(uuid);
    }

    public void addDiscord(String id) {
        if (hasDiscord && rankExpiresAt != 0) Discord.removeRankRole(discordId);

        hasDiscord = true;
        discordId = id;

        if (rankExpiresAt != 0) Discord.addRankRole(id);
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();

        tag.put("uuid", NBT.toTag(uuid));

        tag.putInt("totalVotes", totalVotes);
        tag.putInt("offlineVotes", offlineVotes);

        tag.putLong("rankExpiresAt", rankExpiresAt);
        tag.putBoolean("hadRank", hadRank);

        tag.putBoolean("hasDiscord", hasDiscord);
        tag.putString("discordId", discordId);

        return tag;
    }

    public User fromTag(CompoundTag tag) {
        uuid = NBT.fromTag(tag.getLongArrayTag("uuid"));

        totalVotes = tag.getInt("totalVotes");
        offlineVotes = tag.getInt("offlineVotes");

        rankExpiresAt = tag.getLong("rankExpiresAt");
        hadRank = tag.getBoolean("hadRank");

        hasDiscord = tag.getBoolean("hasDiscord");
        discordId = tag.getString("discordId");

        return this;
    }
}
