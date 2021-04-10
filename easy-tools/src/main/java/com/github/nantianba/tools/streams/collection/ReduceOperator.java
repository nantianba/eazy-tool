package com.github.nantianba.tools.streams.collection;

import java.util.function.Predicate;
import java.util.function.Supplier;

public interface ReduceOperator<T> {

    T first();

    T first(Predicate<T> condition);

    T firstOrDefault(T defaultVal);

    T firstOrDefault(Supplier<T> defaultSupplier);

    T firstOrDefault(Predicate<T> condition, T defaultVal);

    T firstOrDefault(Predicate<T> condition, Supplier<T> defaultSupplier);
}
