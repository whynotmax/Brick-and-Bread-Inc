package dev.mzcy.infrastructure.mongo.codec;

import net.minestom.server.coordinate.Pos;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class PosCodec implements Codec<Pos> {

    /**
     * Decodes a BSON value from the given reader into an instance of the type parameter {@code T}.
     *
     * @param reader         the BSON reader
     * @param decoderContext the decoder context
     * @return an instance of the type parameter {@code T}.
     */
    @Override
    public Pos decode(BsonReader reader, DecoderContext decoderContext) {
        double x;
        double y;
        double z;
        float yaw;
        float pitch;
        reader.readStartDocument();
        x = reader.readDouble("x");
        y = reader.readDouble("y");
        z = reader.readDouble("z");
        yaw = (float) reader.readDouble("yaw");
        pitch = (float) reader.readDouble("pitch");
        reader.readEndDocument();
        return new Pos(x, y, z, yaw, pitch);
    }

    /**
     * Encode an instance of the type parameter {@code T} into a BSON value.
     *
     * @param writer         the BSON writer to encode into
     * @param value          the value to encode
     * @param encoderContext the encoder context
     */
    @Override
    public void encode(BsonWriter writer, Pos value, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeDouble("x", value.x());
        writer.writeDouble("y", value.y());
        writer.writeDouble("z", value.z());
        writer.writeDouble("yaw", value.yaw());
        writer.writeDouble("pitch", value.pitch());
        writer.writeEndDocument();
    }

    /**
     * Returns the Class instance that this encodes. This is necessary because Java does not reify generic types.
     *
     * @return the Class instance that this encodes.
     */
    @Override
    public Class<Pos> getEncoderClass() {
        return Pos.class;
    }
}
