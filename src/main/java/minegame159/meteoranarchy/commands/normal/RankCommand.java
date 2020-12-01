package minegame159.meteoranarchy.commands.normal;

import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.commands.MyCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class RankCommand extends MyCommand {
    private static final String[] benefits = {
            "Discord role",
            "Custom colored nick",
            "Colored item names through anvils",
            "Custom item for the first month",
            "Ability to suggest this month's server motd"
    };

    public RankCommand() {
        super("rank", "Displays all rank benefits.", null, null);
    }

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        sender.sendMessage(MeteorAnarchy.PREFIX + "Rank benefits:");

        for (String s : benefits) {
            sender.sendMessage(MeteorAnarchy.PREFIX + " - " + ChatColor.WHITE + s);
        }

        sender.sendMessage(MeteorAnarchy.PREFIX + "Buy the rank at " + ChatColor.WHITE + "http://store.meteoranarchy.com/");
        return true;
    }
}
