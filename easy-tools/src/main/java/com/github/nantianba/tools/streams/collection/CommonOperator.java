package com.github.nantianba.tools.streams.collection;

import java.util.function.Predicate;

public interface CommonOperator<T> {
    long count(Predicate<T> condition);

    long count();
}
