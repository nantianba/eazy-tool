package com.github.nantianba.tools.streams.collection;

import com.github.nantianba.tools.streams.CollectionStream;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

public interface IterableOperator<T> {
    <R>
    CollectionStream<R> select(Function<T, R> mapper);

    CollectionStream<T> where(Predicate<T> condition);

    CollectionStream<T> orderBy(Comparator<T> comparator);

    CollectionStream<T> orderByDesc(Comparator<T> comparator);

    <R extends Comparable<R>>
    CollectionStream<T> orderBy(Function<T, R> compareFields, Function<T, R>... otherCompareFields);

    <R extends Comparable<R>>
    CollectionStream<T> orderByDesc(Function<T, R> compareFields, Function<T, R>... otherCompareFields);

    <R>
    CollectionStream<R> distinct(Function<T, R> distinctBy);

    CollectionStream<T> distinct();

    CollectionStream<T> limit(int limit);

    CollectionStream<T> reverse();
}
