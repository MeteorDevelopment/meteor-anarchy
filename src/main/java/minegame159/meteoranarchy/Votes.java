package minegame159.meteoranarchy;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import minegame159.meteoranarchy.users.User;
import minegame159.meteoranarchy.utils.VoteReward;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Votes {
    private static final List<VoteReward> rewards = new ArrayList<>();
    private static final Int2ObjectMap<VoteReward> rewardsMap = new Int2ObjectOpenHashMap<>();

    public static void init() {
        rewards.clear();
        rewardsMap.clear();

        add(new VoteReward(75, "Ability to use greentext", player -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set " + Perms.GREENTEXT.getName());
            player.sendMessage(MeteorAnarchy.PREFIX + ChatColor.GREEN + ChatColor.BOLD + "VOTE REWARD: " + ChatColor.GRAY + "You can now use " + ChatColor.WHITE + "greentext" + ChatColor.GRAY + " (put '>' before your message)");
        }));
    }

    private static void add(VoteReward reward) {
        rewards.add(reward);
        rewardsMap.putIfAbsent(reward.votes, reward);
    }

    public static List<VoteReward> getAll() {
        return rewards;
    }

    public static void onVoteAdded(User user, Player player) {
        VoteReward reward = rewardsMap.get(user.totalVotes);
        if (reward != null) reward.callback.accept(player);
    }
}
