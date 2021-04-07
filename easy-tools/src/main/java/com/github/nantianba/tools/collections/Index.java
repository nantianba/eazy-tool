package com.github.nantianba.tools.collections;

import com.github.nantianba.tools.collections.impl.DefaultIndex;

import java.util.function.Function;

/**
 * @param <T>
 * @param <K>
 */
public interface Index<T, K> {
    static <T, K> Index<T, K> of(Function<T, K> function) {
        return new DefaultIndex<>(function);
    }

    Function<T, K> ref();
}
