package minegame159.meteoranarchy.commands.normal;

import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.PrivateMsgs;
import minegame159.meteoranarchy.commands.MyCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand extends MyCommand {
    private final StringBuilder sb = new StringBuilder();

    public MessageCommand() {
        super("msg", "Messages a person.", "/msg <name> <message>", null);
    }

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player) || args.length < 2) return false;
        Player player = (Player) sender;

        Player receiver = Bukkit.getPlayer(args[0]);
        if (receiver == null) {
            player.sendMessage(MeteorAnarchy.PREFIX + ChatColor.WHITE + args[0] + ChatColor.GRAY + " is not online");
            return true;
        }

        for (int i = 1; i < args.length; i++) {
            if (i > 1) sb.append(" ");
            sb.append(args[i]);
        }

        PrivateMsgs.send(player, receiver, sb.toString());
        sb.setLength(0);

        return true;
    }
}
