package dev.mzcy.common.serialization;

import dev.mzcy.common.serialization.exception.DeserializeException;
import dev.mzcy.common.serialization.exception.SerializeException;

public interface Serializer<T, R> {

    /**
     * Serializes an object of type T to an object of type R.
     *
     * @param object the object to serialize
     * @return the serialized object
     */
    R serialize(T object) throws SerializeException;

    /**
     * Deserializes an object of type R to an object of type T.
     *
     * @param object the object to deserialize
     * @return the deserialized object
     */
    T deserialize(R object) throws DeserializeException;

}
