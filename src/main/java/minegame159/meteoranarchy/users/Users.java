package minegame159.meteoranarchy.users;

import minegame159.meteoranarchy.MeteorAnarchy;
import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.ListTag;
import net.querz.nbt.tag.Tag;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Users {
    public static final Users INSTANCE = new Users();

    private final Map<UUID, User> users = new HashMap<>();

    public void load() {
        users.clear();

        File file = new File(MeteorAnarchy.INSTANCE.getDataFolder(), "users.nbt");

        if (file.exists()) {
            try {
                fromTag((CompoundTag) NBTUtil.read(file).getTag());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void save() {
        File file = new File(MeteorAnarchy.INSTANCE.getDataFolder(), "users.nbt");
        file.getParentFile().mkdirs();

        try {
            NBTUtil.write(toTag(), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User get(UUID uuid) {
        User user = users.get(uuid);
        if (user == null) {
            user = new User(uuid);
            users.put(uuid, user);
        }
        return user;
    }

    public User getNoAdd(UUID uuid) {
        User user = users.get(uuid);
        if (user == null) user = new User(uuid);
        return user;
    }

    private CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();

        ListTag<CompoundTag> arr = new ListTag<>(CompoundTag.class);
        for (User user : users.values()) {
            arr.add(user.toTag());
        }
        tag.put("users", arr);

        return tag;
    }

    private void fromTag(CompoundTag tag) {
        for (Tag<?> t : tag.getListTag("users")) {
            User user = new User(null).fromTag((CompoundTag) t);
            users.put(user.uuid, user);
        }
    }
}
