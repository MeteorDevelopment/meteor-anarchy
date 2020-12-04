package minegame159.meteoranarchy.commands.admin;

import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.Perms;
import minegame159.meteoranarchy.commands.MyCommand;
import minegame159.meteoranarchy.listeners.StashLoggerListener;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class StashesCommand extends MyCommand {
    public StashesCommand() {
        super("stashes", "Displays saves stashes count.", null, Perms.ADMIN);
    }

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        sender.sendMessage(MeteorAnarchy.PREFIX + "There are " + ChatColor.WHITE + StashLoggerListener.stashes.size() + ChatColor.GRAY + " saved stashes.");
        return true;
    }
}
