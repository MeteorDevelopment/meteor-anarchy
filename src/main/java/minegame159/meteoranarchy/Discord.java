package minegame159.meteoranarchy;

import minegame159.meteoranarchy.users.User;
import minegame159.meteoranarchy.users.Users;
import minegame159.meteoranarchy.utils.Utils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class Discord {
    private static final Map<String, TokenData> tokens = new HashMap<>();

    private static JDA jda;

    public static void start() {
        if (jda != null) return;

        tokens.clear();

        File file = new File(MeteorAnarchy.INSTANCE.getDataFolder(), "discord.txt");
        String token = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            token = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            jda = JDABuilder.createDefault(token).build();

            jda.addEventListener(new ListenerAdapter() {
                @Override
                public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
                    onMessage(event);
                }
            });
        } catch (LoginException e) {
            e.printStackTrace();
        }

        long delay = 10 * 60 * 1000;
        Bukkit.getScheduler().runTaskTimer(MeteorAnarchy.INSTANCE, () -> {
            long time = System.currentTimeMillis();

            for (Iterator<String> it = tokens.keySet().iterator(); it.hasNext();) {
                TokenData data = tokens.get(it.next());
                if (data.expiresAt <= time) it.remove();
            }
        }, delay, delay);
    }

    public static String addToken(Player player) {
        String token;
        do token = Utils.generateToken().substring(0, 6);
        while (tokens.containsKey(token));

        tokens.put(token, new TokenData(player.getUniqueId(), player.getName()));
        return token;
    }

    private static void onMessage(GuildMessageReceivedEvent event) {
        if (!event.getMessage().isFromGuild() || event.getAuthor().isBot()) return;
        String msg = event.getMessage().getContentRaw();

        if (msg.startsWith(".link ")) {
            String[] split = msg.split(" ");
            if (split.length != 2) event.getMessage().getChannel().sendMessage("You need to enter a valid token").queue();
            else {
                TokenData data = tokens.remove(split[1]);

                if (data == null) event.getMessage().getChannel().sendMessage("You need to enter a valid token").queue();
                else {
                    User user = Users.INSTANCE.get(data.uuid);
                    user.addDiscord(event.getAuthor().getId());

                    event.getMessage().getChannel().sendMessage("Linked your discord account to `" + data.name + "`").queue();
                }
            }
        }
    }

    public static void addRankRole(String id) {
        Guild guild = jda.getGuildById("781786592129581086");
        guild.addRoleToMember(id, guild.getRoleById("783390461129392200")).queue();
    }

    public static void removeRankRole(String id) {
        Guild guild = jda.getGuildById("781786592129581086");
        guild.removeRoleFromMember(id, guild.getRoleById("783390461129392200")).queue();
    }

    public static void stop() {
        if (jda == null) return;

        jda.shutdownNow();
        jda = null;
    }

    private static class TokenData {
        public final UUID uuid;
        public final String name;
        public final long expiresAt;

        public TokenData(UUID uuid, String name) {
            this.uuid = uuid;
            this.name = name;
            this.expiresAt = System.currentTimeMillis() + 10 * 60 * 1000;
        }
    }
}
