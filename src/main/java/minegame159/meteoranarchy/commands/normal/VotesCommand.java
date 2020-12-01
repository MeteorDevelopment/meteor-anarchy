package minegame159.meteoranarchy.commands.normal;

import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.commands.MyCommand;
import minegame159.meteoranarchy.users.User;
import minegame159.meteoranarchy.users.Users;
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

        User user = Users.INSTANCE.getNoAdd(((Player) sender).getUniqueId());
        sender.sendMessage(MeteorAnarchy.PREFIX + "You have " + ChatColor.WHITE + user.totalVotes + ChatColor.GRAY + " votes.");

        return true;
    }
}
