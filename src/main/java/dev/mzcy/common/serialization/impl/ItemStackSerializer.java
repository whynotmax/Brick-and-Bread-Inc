package dev.mzcy.common.serialization.impl;

import dev.mzcy.common.serialization.Serializer;
import dev.mzcy.common.serialization.exception.DeserializeException;
import dev.mzcy.common.serialization.exception.SerializeException;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.TagStringIOExt;
import net.kyori.examination.string.StringExaminer;
import net.minestom.server.adventure.serializer.nbt.NbtComponentSerializer;
import net.minestom.server.item.ItemStack;

import java.util.Base64;

public class ItemStackSerializer implements Serializer<ItemStack, String> {
    /**
     * Serializes an object of type T to an object of type R.
     *
     * @param object the object to serialize
     * @return the serialized object
     */
    @Override
    public String serialize(ItemStack object) throws SerializeException {
        try {
            if (object == null || object.isAir()) return "";
            CompoundBinaryTag nbt = object.toItemNBT();
            return Base64.getEncoder().encodeToString(TagStringIOExt.writeTag(nbt).getBytes());
        } catch (Exception e) {
            throw new SerializeException("Failed to serialize ItemStack", e);
        }
    }

    /**
     * Deserializes an object of type R to an object of type T.
     *
     * @param object the object to deserialize
     * @return the deserialized object
     */
    @Override
    public ItemStack deserialize(String object) throws DeserializeException {
        try {
            if (object == null || object.isEmpty()) return ItemStack.AIR;
            byte[] bytes = Base64.getDecoder().decode(object);
            CompoundBinaryTag nbt = CompoundBinaryTag.empty().put("", TagStringIOExt.readTag(new String(bytes)));
            return ItemStack.fromItemNBT(nbt);
        } catch (Exception e) {
            throw new DeserializeException("Failed to deserialize ItemStack", e);
        }
    }
}
