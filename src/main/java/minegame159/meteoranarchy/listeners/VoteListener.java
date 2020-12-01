package minegame159.meteoranarchy.listeners;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.users.User;
import minegame159.meteoranarchy.users.Users;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VoteListener implements Listener {
    @EventHandler
    private void onVote(VotifierEvent event) {
        Vote vote = event.getVote();

        Player player = Bukkit.getPlayer(vote.getUsername());
        if (player != null) {
            User user = Users.INSTANCE.get(player.getUniqueId());
            user.totalVotes++;

            player.sendMessage(MeteorAnarchy.PREFIX + "Thanks for voting on " + ChatColor.WHITE + vote.getServiceName() + ChatColor.GRAY + "." + ChatColor.WHITE + user.totalVotes + ChatColor.GRAY + " votes");
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(MeteorAnarchy.INSTANCE, () -> {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(vote.getUsername());
                User user = Users.INSTANCE.get(offlinePlayer.getUniqueId());
                user.totalVotes++;
            });
        }
    }
}
