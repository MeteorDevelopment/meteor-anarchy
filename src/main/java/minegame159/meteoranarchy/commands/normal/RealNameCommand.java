package minegame159.meteoranarchy.commands.normal;

import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.Nicks;
import minegame159.meteoranarchy.commands.MyCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class RealNameCommand extends MyCommand {
    public RealNameCommand() {
        super("realname", "Shows the real name of a person", "/realname <nick>", null);
    }

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (args.length != 1) return false;

        String realName = Nicks.getRealName(args[0]);

        if (realName != null) sender.sendMessage(MeteorAnarchy.PREFIX + "Real name of " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', args[0]) + ChatColor.GRAY + " is " + ChatColor.WHITE + realName);
        else sender.sendMessage(MeteorAnarchy.PREFIX + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', args[0]) + ChatColor.GRAY + " is not nicked");

        return true;
    }
}
