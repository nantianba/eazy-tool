package com.github.nantianba.tools.streams.collection;

import java.util.Comparator;
import java.util.function.*;

public interface ReduceOperator<T> {

    T first();

    T first(Predicate<T> condition);

    T firstOrDefault(T defaultVal);

    T firstOrDefault(Supplier<T> defaultSupplier);

    T firstOrDefault(Predicate<T> condition, T defaultVal);

    T firstOrDefault(Predicate<T> condition, Supplier<T> defaultSupplier);

    /**
     * convert elements to double and compute averaging value
     */
    double averagingDouble(ToDoubleFunction<? super T> mapper);

    int averagingInt(ToIntFunction<? super T> mapper);

    long averagingLong(ToLongFunction<? super T> mapper);

    double summingDouble(ToDoubleFunction<? super T> mapper);

    int summingInt(ToIntFunction<? super T> mapper);

    long summingLong(ToLongFunction<? super T> mapper);

    T min(Comparator<? super T> comparator);

    T max(Comparator<? super T> comparator);

}
