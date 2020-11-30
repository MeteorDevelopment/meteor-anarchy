package minegame159.meteoranarchy;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;

public class Perms {
    public static final Permission RANK = new Permission("meteoranarchy.rank");

    public static void init() {
        Bukkit.getPluginManager().addPermission(RANK);
    }
}
