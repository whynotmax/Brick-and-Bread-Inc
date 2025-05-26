package dev.mzcy.data.mongo.codec;

import dev.mzcy.utility.serialization.Serializers;
import net.minestom.server.item.ItemStack;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class ItemStackCodec implements Codec<ItemStack> {

    /**
     * Decodes a BSON value from the given reader into an instance of the type parameter {@code T}.
     *
     * @param reader         the BSON reader
     * @param decoderContext the decoder context
     * @return an instance of the type parameter {@code T}.
     */
    @Override
    public ItemStack decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        String base64 = reader.readString();
        reader.readEndDocument();
        return Serializers.ITEM_STACK_SERIALIZER.deserialize(base64);
    }

    /**
     * Encode an instance of the type parameter {@code T} into a BSON value.
     *
     * @param writer         the BSON writer to encode into
     * @param value          the value to encode
     * @param encoderContext the encoder context
     */
    @Override
    public void encode(BsonWriter writer, ItemStack value, EncoderContext encoderContext) {
        String base64 = Serializers.ITEM_STACK_SERIALIZER.serialize(value);
        writer.writeStartDocument();
        writer.writeString(base64);
        writer.writeEndDocument();
    }

    /**
     * Returns the Class instance that this encodes. This is necessary because Java does not reify generic types.
     *
     * @return the Class instance that this encodes.
     */
    @Override
    public Class<ItemStack> getEncoderClass() {
        return ItemStack.class;
    }
}
