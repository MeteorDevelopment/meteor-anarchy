package minegame159.meteoranarchy.commands.normal;

import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.commands.MyCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class VoteCommand extends MyCommand {
    private static final String[] links = {
            "https://minecraft-server-list.com/server/470082/",
            "https://minecraftservers.org/server/599803/",
            "https://www.minecraft-servers-list.org/details/MineGame159/",
            "https://mc-lists.org/server-meteor-anarchy.1161/",
            "https://minecraft-server.net/vote/MeteorAnarchy/"
    };

    public VoteCommand() {
        super("vote", "Displays all links to vote on.", null, null);
    }

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        sender.sendMessage(MeteorAnarchy.PREFIX + "There are " + ChatColor.WHITE + links.length + ChatColor.GRAY + " links to vote on:");

        for (int i = 0; i < links.length; i++) {
            sender.sendMessage(MeteorAnarchy.PREFIX + ChatColor.WHITE + (i + 1) + ". " + ChatColor.GRAY + links[i]);
        }

        return true;
    }
}
