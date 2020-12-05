package minegame159.meteoranarchy.commands.normal;

import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.Votes;
import minegame159.meteoranarchy.commands.MyCommand;
import minegame159.meteoranarchy.users.User;
import minegame159.meteoranarchy.users.Users;
import minegame159.meteoranarchy.utils.VoteReward;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VotesCommand extends MyCommand {
    public VotesCommand() {
        super("votes", "Displays how many total votes you have.", null, null);
    }

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        User user = Users.INSTANCE.getNoAdd(player.getUniqueId());
        sender.sendMessage(MeteorAnarchy.PREFIX + "You have " + ChatColor.WHITE + user.totalVotes + ChatColor.GRAY + " votes.");

        for (VoteReward reward : Votes.getAll()) {
            player.sendMessage(reward.getMsg(user));
        }

        return true;
    }
}
