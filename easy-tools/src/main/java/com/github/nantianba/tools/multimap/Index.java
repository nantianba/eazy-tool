package com.github.nantianba.tools.multimap;

import com.github.nantianba.tools.multimap.impl.DefaultIndex;

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
