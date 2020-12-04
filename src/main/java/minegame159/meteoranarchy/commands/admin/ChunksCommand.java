package minegame159.meteoranarchy.commands.admin;

import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.Perms;
import minegame159.meteoranarchy.commands.MyCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class ChunksCommand extends MyCommand {
    public ChunksCommand() {
        super("chunks", "Displays how many chunks are loaded.", null, Perms.ADMIN);
    }

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        int loadedChunks = 0;
        for (World world : Bukkit.getWorlds()) loadedChunks += world.getLoadedChunks().length;

        sender.sendMessage(MeteorAnarchy.PREFIX + "There are " + ChatColor.WHITE + loadedChunks + ChatColor.GRAY + " loaded chunks");
        return true;
    }
}
