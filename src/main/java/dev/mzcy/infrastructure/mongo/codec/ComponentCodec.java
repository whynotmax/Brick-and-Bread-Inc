package dev.mzcy.infrastructure.mongo.codec;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class ComponentCodec implements Codec<Component> {

    /**
     * Decodes a BSON value from the given reader into an instance of the type parameter {@code T}.
     *
     * @param reader         the BSON reader
     * @param decoderContext the decoder context
     * @return an instance of the type parameter {@code T}.
     */
    @Override
    public Component decode(BsonReader reader, DecoderContext decoderContext) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        reader.readStartDocument();
        String text = reader.readString("text");
        reader.readEndDocument();
        return miniMessage.deserialize(text);
    }

    /**
     * Encode an instance of the type parameter {@code T} into a BSON value.
     *
     * @param writer         the BSON writer to encode into
     * @param value          the value to encode
     * @param encoderContext the encoder context
     */
    @Override
    public void encode(BsonWriter writer, Component value, EncoderContext encoderContext) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        String text = miniMessage.serialize(value);
        writer.writeStartDocument();
        writer.writeString("text", text);
        writer.writeEndDocument();
    }

    /**
     * Returns the Class instance that this encodes. This is necessary because Java does not reify generic types.
     *
     * @return the Class instance that this encodes.
     */
    @Override
    public Class<Component> getEncoderClass() {
        return Component.class;
    }
}
