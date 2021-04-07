package com.github.nantianba.tools.collections.impl;

import com.github.nantianba.tools.collections.Index;
import com.github.nantianba.tools.collections.excception.MultiIndicesMapException;

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
