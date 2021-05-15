package com.github.nantianba.tools.streams.builder;

import com.github.nantianba.tools.streams.CollectionStream;

import java.util.Collection;
import java.util.stream.Stream;

public interface StreamBuilder {
    <T> CollectionStream<T> of(Collection<T> collection);

    <T> CollectionStream<T> of(T[] tCollection);

    <T> CollectionStream<T> of(Stream<T> stream);
}
