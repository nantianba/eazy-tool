package com.github.nantianba.tools.multimap.impl;

import com.github.nantianba.tools.multimap.Index;
import com.github.nantianba.tools.multimap.excception.MultiIndicesMapException;

import java.util.function.Function;

public class DefaultIndex<T, K> implements Index<T, K> {

    private final Function<T, K> function;

    public DefaultIndex(Function<T, K> function) {
        if (function == null) {
            throw new MultiIndicesMapException("fucntion is null");
        }
        this.function = function;
    }

    @Override
    public Function<T, K> ref() {
        return function;
    }
}
