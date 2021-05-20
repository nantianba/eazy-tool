package com.github.nantianba.tools.streams.collection;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

public interface CollectOperator<T> extends Stream<T> {
    /**
     * convert the collection to anther collection
     */
    <C extends Collection<T>>
    C toCollection(Supplier<C> supplier);


    Set<T> toSet();

    Set<T> toSet(Supplier<Set<T>> supplier);


    List<T> toList();

    List<T> toList(Supplier<List<T>> supplier);

    ArrayList<T> toArrayList();

    <K>
    Map<K, T> toMap(Function<? super T, ? extends K> keyMapper);

    <K, V>
    Map<K, V> toMap(Function<? super T, ? extends K> keyMapper,
                    Function<? super T, ? extends V> valueMapper);

    <K, V>
    Map<K, List<V>> groupingBy(Function<? super T, ? extends K> keyMapper);

    <K, C extends Collection<T>>
    Map<K, C> groupingBy(Function<? super T, ? extends K> keyMapper,
                      Supplier<C> downstreamSupplier);

    <K, V, C extends Collection<V>>
    Map<K, C> groupingBy(Function<? super T, ? extends K> keyMapper,
                      Function<? super T, ? extends V> valueMapper,
                      Supplier<C> downstreamSupplier);


}
