package minegame159.meteoranarchy.users;

import minegame159.meteoranarchy.utils.NBT;
import net.querz.nbt.tag.CompoundTag;

import java.util.UUID;

public class User {
    public UUID uuid;

    public int totalVotes;

    public long rankExpiresAt;
    public boolean hadRank;

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();

        tag.put("uuid", NBT.toTag(uuid));

        tag.putInt("totalVotes", totalVotes);

        tag.putLong("rankExpiresAt", rankExpiresAt);
        tag.putBoolean("hadRank", hadRank);

        return tag;
    }

    public User fromTag(CompoundTag tag) {
        uuid = NBT.fromTag(tag.getLongArrayTag("uuid"));

        totalVotes = tag.getInt("totalVotes");

        rankExpiresAt = tag.getLong("rankExpiresAt");
        hadRank = tag.getBoolean("hadRank");

        return this;
    }
}
