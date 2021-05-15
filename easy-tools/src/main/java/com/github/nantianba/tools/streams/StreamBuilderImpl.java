package com.github.nantianba.tools.streams;

import com.github.nantianba.tools.streams.builder.StreamBuilder;

import java.util.Collection;
import java.util.stream.Stream;

class StreamBuilderImpl implements StreamBuilder {

    @Override
    public <T> CollectionStream<T> of(Collection<T> collection) {
        return null;
    }

    @Override
    public <T> CollectionStream<T> of(T[] tCollection) {
        return null;
    }

    @Override
    public <T> CollectionStream<T> of(Stream<T> stream) {
        return null;
    }
}
