package minegame159.meteoranarchy;

import email.com.gmail.cosmoconsole.bukkit.deathmsg.DeathMessagesPrime;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Nicks {
    private static final Map<UUID, String> nicks = new HashMap<>();
    private static final Map<String, UUID> nicks2 = new HashMap<>();

    public static void load() {
        nicks.clear();
        nicks2.clear();

        try {
            File file = new File(MeteorAnarchy.INSTANCE.getDataFolder(), "nicks.txt");
            MeteorAnarchy.INSTANCE.getDataFolder().mkdirs();

            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));

                reader.lines().forEach(s -> {
                    String[] split = s.split(" ");
                    UUID uuid = UUID.fromString(split[0]);

                    nicks.put(uuid, split[1]);
                    nicks2.put(split[1], uuid);
                });

                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        new PapiExpansion().register();

        DeathMessagesPrime.getPlugin(DeathMessagesPrime.class).registerTag(MeteorAnarchy.INSTANCE, "meteoranarchy_nick", (s, player, damageCause, entity) -> getTextComponent(player));
        DeathMessagesPrime.getPlugin(DeathMessagesPrime.class).registerTag(MeteorAnarchy.INSTANCE, "meteoranarchy_nick_killer", (s, player, damageCause, entity) -> {
            if (!(entity instanceof Player)) return new TextComponent("");
            return getTextComponent((Player) entity);
        });
    }

    private static TextComponent getTextComponent(Player player) {
        String nick = get(player);
        String realName = getRealName(nick);

        if (realName == null) realName = nick;

        TextComponent c = new TextComponent(ChatColor.translateAlternateColorCodes('&', nick));
        c.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.translateAlternateColorCodes('&', realName))));
        return c;
    }

    public static void save() {
        try {
            File file = new File(MeteorAnarchy.INSTANCE.getDataFolder(), "nicks.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (UUID uuid : nicks.keySet()) {
                writer.write(uuid.toString());
                writer.write(" ");
                writer.write(nicks.get(uuid));
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean set(Player player, String nick) {
        if (nicks2.containsKey(nick)) return false;

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p != player && p.getName().equals(nick)) return false;
        }

        String prevNick = nicks.get(player.getUniqueId());
        if (prevNick != null) nicks2.remove(prevNick);

        nicks.put(player.getUniqueId(), nick);
        nicks2.put(nick, player.getUniqueId());
        return true;
    }

    public static void reset(UUID uuid) {
        String nick = nicks.remove(uuid);
        if (nick != null) nicks2.remove(nick);
    }
    public static void reset(Player player) {
        reset(player.getUniqueId());
    }

    public static String get(Player player) {
        String nick = nicks.get(player.getUniqueId());
        if (nick != null) return nick;
        return player.getName();
    }

    public static String getRealName(String nick) {
        UUID uuid = null;
        for (String n : nicks2.keySet()) {
            if (ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', n)).equalsIgnoreCase(nick)) {
                uuid = nicks2.get(n);
                break;
            }
        }

        if (uuid == null) return null;
        return Bukkit.getOfflinePlayer(uuid).getName();
    }

    private static class PapiExpansion extends PlaceholderExpansion {
        @Override
        public boolean persist() {
            return true;
        }

        @Override
        public String getIdentifier() {
            return "meteoranarchy";
        }

        @Override
        public String getAuthor() {
            return "MineGame159";
        }

        @Override
        public String getVersion() {
            return "0.1.0";
        }

        @Override
        public String onPlaceholderRequest(Player player, String params) {
            if (params.equals("nick")) return Nicks.get(player);

            return null;
        }
    }
}
