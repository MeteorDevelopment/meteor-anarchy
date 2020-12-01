package minegame159.meteoranarchy.commands.normal;

import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.Nicks;
import minegame159.meteoranarchy.commands.MyCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NameCommand extends MyCommand {
    public NameCommand() {
        super("name", "Displays player's nick", "/name <name>", null);
    }

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (args.length != 1) return false;

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(MeteorAnarchy.PREFIX + "This player is not online");
            return true;
        }

        String nick = Nicks.get(player);
        sender.sendMessage(MeteorAnarchy.PREFIX + ChatColor.WHITE + player.getName() + ChatColor.GRAY + "'s nick is " + ChatColor.WHITE + nick);

        return true;
    }
}
