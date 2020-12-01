package minegame159.meteoranarchy.commands.normal;

import minegame159.meteoranarchy.Discord;
import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.commands.MyCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DiscordCommand extends MyCommand {
    public DiscordCommand() {
        super("discord", "Displays discord invite.", null, null);
    }

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        sender.sendMessage(MeteorAnarchy.PREFIX + "Discord: " + ChatColor.WHITE + "https://discord.gg/JK9uhy8Jrn");
        sender.sendMessage(MeteorAnarchy.PREFIX + "Discord link command: " + ChatColor.WHITE + ".link " + Discord.addToken((Player) sender));

        return true;
    }
}
