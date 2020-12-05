package minegame159.meteoranarchy;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;

public class Perms {
    public static final Permission ADMIN = new Permission("meteoranarchy.admin");
    public static final Permission RANK = new Permission("meteoranarchy.rank");
    public static final Permission GREENTEXT = new Permission("meteoranarchy.greentext");

    public static void init() {
        Bukkit.getPluginManager().addPermission(RANK);
        Bukkit.getPluginManager().addPermission(ADMIN);
        Bukkit.getPluginManager().addPermission(GREENTEXT);
    }
}
