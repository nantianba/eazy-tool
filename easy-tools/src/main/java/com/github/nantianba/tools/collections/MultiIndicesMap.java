package com.github.nantianba.tools.collections;

import com.github.nantianba.tools.collections.impl.DefaultMultiIndicesMap;

import java.util.Collection;
import java.util.List;

/**
 * A map has multiple indices
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

