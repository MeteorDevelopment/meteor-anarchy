package minegame159.meteoranarchy.commands.admin;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import minegame159.meteoranarchy.MeteorAnarchy;
import minegame159.meteoranarchy.Perms;
import minegame159.meteoranarchy.commands.MyCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EntitiesCommand extends MyCommand {
    private final Object2IntMap<EntityType> entities = new Object2IntOpenHashMap<>();
    private final List<Object2IntMap.Entry<EntityType>> sortList = new ArrayList<>();

    public EntitiesCommand() {
        super("entities", "Displays entity counts.", null, Perms.ADMIN);
    }

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        int totalEntities = 0;
        for (World world : Bukkit.getWorlds()) totalEntities += world.getEntityCount();

        sender.sendMessage(MeteorAnarchy.PREFIX + "There are " + ChatColor.WHITE + totalEntities + ChatColor.GRAY + " total entities");

        /*for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                try {
                    entities.put(entity.getType(), entities.getInt(entity.getType()) + 1);
                } catch (NullPointerException ignored) {}
            }
        }

        sortEntities();
        for (int i = 0; i < 10; i++) {
            if (i >= sortList.size()) break;
            Object2IntMap.Entry<EntityType> entry = sortList.get(i);

            sender.sendMessage(MeteorAnarchy.PREFIX + (i + 1) + ". " + ChatColor.WHITE + entry.getKey().getName() + ChatColor.GRAY + " - " + ChatColor.WHITE + entry.getIntValue());
        }

        sortList.clear();*/
        return true;
    }

    private void sortEntities() {
        sortList.addAll(entities.object2IntEntrySet());
        sortList.sort(Comparator.comparingInt(value -> -value.getIntValue()));

        entities.clear();
    }
}
