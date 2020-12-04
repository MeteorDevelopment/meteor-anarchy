package minegame159.meteoranarchy.commands.normal;

import minegame159.meteoranarchy.Ignores;
import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.commands.MyCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class IgnoreCommand extends MyCommand {
    private final StringBuilder sb = new StringBuilder();

    public IgnoreCommand() {
        super("ignore", "Ignore people.", null, null);
    }

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if (args.length == 0) {
            sender.sendMessage(MeteorAnarchy.PREFIX + "To add / remove people do " + ChatColor.WHITE + " /ignore <name>");

            sb.append(MeteorAnarchy.PREFIX).append("Currently ignoring: ").append(ChatColor.WHITE);
            int i = 0;
            for (UUID ignored : Ignores.getIgnores(player)) {
                if (i > 0) sb.append(", ");
                sb.append(Bukkit.getOfflinePlayer(ignored).getName());
                i++;
            }

            player.sendMessage(sb.toString());
            sb.setLength(0);
        } else if (args.length == 1) {
            OfflinePlayer toIgnore = Bukkit.getOfflinePlayer(args[0]);

            if (toIgnore.hasPlayedBefore()) {
                boolean added = Ignores.toggleIgnore(player, toIgnore.getUniqueId());

                if (added) player.sendMessage(MeteorAnarchy.PREFIX + "Added " + ChatColor.WHITE + toIgnore.getName() + ChatColor.GRAY + " to ignore list");
                else player.sendMessage(MeteorAnarchy.PREFIX + "Removed " + ChatColor.WHITE + toIgnore.getName() + ChatColor.GRAY + " from ignore list");
            } else {
                player.sendMessage(MeteorAnarchy.PREFIX + ChatColor.WHITE + args[0] + ChatColor.GRAY + " never played here before");
            }
        }

        return true;
    }
}
