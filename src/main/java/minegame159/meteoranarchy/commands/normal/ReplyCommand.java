package minegame159.meteoranarchy.commands.normal;

import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.PrivateMsgs;
import minegame159.meteoranarchy.commands.MyCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReplyCommand extends MyCommand {
    private final StringBuilder sb = new StringBuilder();

    public ReplyCommand() {
        super("r", "Replies to the last message.", "/r <message>", null);
    }

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        for (int i = 0; i < args.length; i++) {
            if (i > 0) sb.append(" ");
            sb.append(args[i]);
        }

        if (!PrivateMsgs.reply(player, sb.toString())) {
            player.sendMessage(MeteorAnarchy.PREFIX + "Nobody sent you a message or is offline");
        }
        sb.setLength(0);

        return true;
    }
}
