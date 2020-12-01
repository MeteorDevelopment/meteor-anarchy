package minegame159.meteoranarchy.commands.rank;

import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.Perms;
import minegame159.meteoranarchy.commands.MyCommand;
import minegame159.meteoranarchy.users.User;
import minegame159.meteoranarchy.users.Users;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RankTimeCommand extends MyCommand {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MMM d yyyy");
    
    public RankTimeCommand() {
        super("ranktime", "Displays for how long you ahve bought the rank.", null, Perms.RANK);
    }

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        User user = Users.INSTANCE.getNoAdd(player.getUniqueId());
        if (user.rankExpiresAt != 0) {
            player.sendMessage(MeteorAnarchy.PREFIX + "Your rank expires on " + ChatColor.WHITE + DATE_FORMAT.format(new Date(user.rankExpiresAt)));
        }

        return true;
    }
}
