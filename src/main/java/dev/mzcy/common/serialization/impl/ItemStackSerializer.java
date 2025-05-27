package dev.mzcy.common.serialization.impl;

import dev.mzcy.common.serialization.Serializer;
import dev.mzcy.common.serialization.exception.DeserializeException;
import dev.mzcy.common.serialization.exception.SerializeException;
import net.minestom.server.item.ItemStack;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;
import org.jglrxavpok.hephaistos.nbt.NBTReader;

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
        if (object == null) {
            return null;
        }
        try {
            NBTCompound nbt = object.toItemNBT();
            byte[] nbtBytes = nbt.toByteArray();
            return Base64.getEncoder().encodeToString(nbtBytes);
        } catch (Exception e) {
            throw new SerializeException("Failed to serialize ItemStack: " + e.getMessage(), e);
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
        if (object == null || object.isEmpty()) {
            return null;
        }
        byte[] nbtBytes = Base64.getDecoder().decode(object);
        try (NBTReader nbtReader = NBTReader.fromArray(nbtBytes)) {
            return ItemStack.fromItemNBT(((NBTCompound) nbtReader.read()));
        } catch (Exception e) {
            throw new DeserializeException("Failed to deserialize ItemStack: " + e.getMessage(), e);
        }
    }
}
