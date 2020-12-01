package minegame159.meteoranarchy.commands.normal;

import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.commands.MyCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JoinDateCommand extends MyCommand {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MMM d yyyy");

    public JoinDateCommand() {
        super("joindate", "Displays join date of a player.", "/joindate <name>", null);
    }

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (args.length != 1) return false;

        Player player = Bukkit.getPlayer(args[0]);
        if (player != null) sendMsg(sender, player.getName(), player.getFirstPlayed());
        else {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
            sendMsg(sender, offlinePlayer.getName(), offlinePlayer.getFirstPlayed());
        }

        return true;
    }

    private void sendMsg(CommandSender sender, String name, long date) {
        if (date == 0) sender.sendMessage(MeteorAnarchy.PREFIX + ChatColor.WHITE + name + ChatColor.GRAY + " has never joined");
        else sender.sendMessage(MeteorAnarchy.PREFIX + ChatColor.WHITE + name + ChatColor.GRAY + " joined on " + ChatColor.WHITE + DATE_FORMAT.format(new Date(date)));
    }
}
