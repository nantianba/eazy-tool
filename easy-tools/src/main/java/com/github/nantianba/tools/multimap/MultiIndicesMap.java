package com.github.nantianba.tools.multimap;

import com.github.nantianba.tools.multimap.impl.DefaultMultiIndicesMap;

import java.util.Collection;
import java.util.List;

/**
 * A map has multiple keys,element can be searched by one of them
 *
 * @param <T> generic type for elements
 */
public interface MultiIndicesMap<T> {
    /**
     * a map must be given a collection and at least one index,
     * <p>
     * or an exception will be thrown
     *
     * @return a default implementation
     */
    static <Type> MultiIndicesMap<Type> of(Collection<Type> collection, Index<Type, ?>... indices) {
        return new DefaultMultiIndicesMap<>(collection, indices);
    }

    <K> List<T> get(Index<T, K> index, K key);

    <K> T getFirst(Index<T, K> index, K key);

    <K> boolean contains(Index<T, K> index, K key);

    int size();

    List<T> getRawData();
}

