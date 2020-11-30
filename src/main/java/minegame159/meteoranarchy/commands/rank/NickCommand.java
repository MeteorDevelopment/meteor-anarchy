package minegame159.meteoranarchy.commands.rank;

import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.Nicks;
import minegame159.meteoranarchy.Perms;
import minegame159.meteoranarchy.commands.MyCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand extends MyCommand {
    public NickCommand() {
        super("nick", "Sets or resets your nickname", "/nick <name>", Perms.RANK);
    }

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if (args.length > 0) {
            if (!Nicks.set(player, args[0])) {
                player.sendMessage(MeteorAnarchy.PREFIX + "Someone is already using " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', args[0]));
                return true;
            }
        } else {
            Nicks.reset(player);
        }

        player.sendMessage(MeteorAnarchy.PREFIX + "Your nick changed to " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Nicks.get(player)));
        return true;
    }
}
