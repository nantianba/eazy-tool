package com.github.nantianba.tools.streams;

import com.github.nantianba.tools.streams.builder.StreamBuilder;
import com.github.nantianba.tools.streams.type.CollectionStream;
import lombok.experimental.UtilityClass;

import java.util.Collection;

/**
 * A easy wrapper for java stream operation
 */
@UtilityClass
public class Streams {

    private StreamBuilder builder;

    public static <T> CollectionStream of(Collection<T> tCollection) {
        return builder.of(tCollection);
    }

    public static <T> CollectionStream of(T[] tCollection) {
        return builder.of(tCollection);
    }
}
