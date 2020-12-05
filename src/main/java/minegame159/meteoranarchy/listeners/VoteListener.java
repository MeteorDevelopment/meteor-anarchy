package minegame159.meteoranarchy.listeners;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.Votes;
import minegame159.meteoranarchy.users.User;
import minegame159.meteoranarchy.users.Users;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class VoteListener implements Listener {
    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        User user = Users.INSTANCE.getNoAdd(event.getPlayer().getUniqueId());

        for (int i = 0; i < user.offlineVotes; i++) {
            user.totalVotes++;
            Votes.onVoteAdded(user, event.getPlayer());
        }

        user.offlineVotes = 0;
    }

    @EventHandler
    private void onVote(VotifierEvent event) {
        Vote vote = event.getVote();

        Player player = Bukkit.getPlayer(vote.getUsername());
        if (player != null) {
            User user = Users.INSTANCE.get(player.getUniqueId());

            user.totalVotes++;
            Votes.onVoteAdded(user, player);

            player.sendMessage(MeteorAnarchy.PREFIX + "Thanks for voting on " + ChatColor.WHITE + vote.getServiceName() + ChatColor.GRAY + ". " + ChatColor.WHITE + user.totalVotes + ChatColor.GRAY + " votes");
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(MeteorAnarchy.INSTANCE, () -> {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(vote.getUsername());
                User user = Users.INSTANCE.get(offlinePlayer.getUniqueId());
                user.offlineVotes++;
            });
        }
    }
}
