package com.github.nantianba.tools.streams.collection;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface IterableOperator<T> {
    <R> IterableOperator<R> select(Function<T, R> mapper);

    IterableOperator<T> where(Predicate<T> condition);

    IterableOperator<T> orderBy(Comparator<T> comparator);

    IterableOperator<T> orderByDesc(Comparator<T> comparator);

    <R extends Comparable<R>> IterableOperator<T> orderBy(Function<T, R> compareFields, Function<T, R>... otherCompareFields);

    <R extends Comparable<R>> IterableOperator<T> orderByDesc(Function<T, R> compareFields, Function<T, R>... otherCompareFields);

    <R> IterableOperator<R> distinct(Function<T, R> keyMapper);

    IterableOperator<T> distinct();

    IterableOperator<T> limit(int limit);
}
