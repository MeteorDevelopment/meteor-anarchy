package minegame159.meteoranarchy.utils;

import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.users.User;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class VoteReward {
    public final int votes;
    public final String description;
    public final Consumer<Player> callback;

    public VoteReward(int votes, String description, Consumer<Player> callback) {
        this.votes = votes;
        this.description = description;
        this.callback = callback;
    }

    public String getMsg(User user) {
        return MeteorAnarchy.PREFIX + (user.totalVotes >= votes ? ChatColor.GREEN : ChatColor.RED) + votes + ChatColor.GRAY + " - " + ChatColor.WHITE + description;
    }
}
