package minegame159.meteoranarchy.commands.rank;

import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.Perms;
import minegame159.meteoranarchy.commands.MyCommand;
import minegame159.meteoranarchy.users.User;
import minegame159.meteoranarchy.users.Users;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class RankTimeCommand extends MyCommand {
    public RankTimeCommand() {
        super("ranktime", "Displays for how long you ahve bought the rank.", null, Perms.RANK);
    }

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        User user = Users.INSTANCE.getNoAdd(player.getUniqueId());
        if (user.rankExpiresAt != 0) {
            player.sendMessage(MeteorAnarchy.PREFIX + "Your rank expires in " + ChatColor.WHITE + TimeUnit.MILLISECONDS.toDays(user.rankExpiresAt - System.currentTimeMillis()) + ChatColor.GRAY + " days");
        }

        return true;
    }
}
