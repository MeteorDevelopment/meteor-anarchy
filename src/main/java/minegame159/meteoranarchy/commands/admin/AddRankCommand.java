package minegame159.meteoranarchy.commands.admin;

import minegame159.meteoranarchy.Perms;
import minegame159.meteoranarchy.commands.MyCommand;
import minegame159.meteoranarchy.users.User;
import minegame159.meteoranarchy.users.Users;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddRankCommand extends MyCommand {
    public AddRankCommand() {
        super("addrank", "Extends player's rank.", "/addrank <name> <months>", Perms.RANK);
    }

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (args.length != 2) return false;

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) return false;

        int months;
        try {
            months = Integer.parseInt(args[1]);
        } catch (NumberFormatException ignored) {
            return false;
        }

        User user = Users.INSTANCE.get(player.getUniqueId());
        user.addBenefits(player);
        user.rankExpiresAt += months * 30 * 24 * 60 * 60 * 1000;

        return true;
    }
}
