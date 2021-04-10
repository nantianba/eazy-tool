package com.github.nantianba.tools.streams.builder;

import com.github.nantianba.tools.streams.type.CollectionStream;

import java.util.Collection;

public interface StreamBuilder {
    <T> CollectionStream of(Collection<T> tCollection);

    <T> CollectionStream of(T[] tCollection);


}
