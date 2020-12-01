package minegame159.meteoranarchy.utils;

import net.querz.nbt.tag.LongArrayTag;

import java.util.UUID;

public class NBT {
    public static LongArrayTag toTag(UUID uuid) {
        return new LongArrayTag(new long[] { uuid.getMostSignificantBits(), uuid.getLeastSignificantBits() });
    }

    public static UUID fromTag(LongArrayTag tag) {
        return new UUID(tag.getValue()[0], tag.getValue()[1]);
    }
}
