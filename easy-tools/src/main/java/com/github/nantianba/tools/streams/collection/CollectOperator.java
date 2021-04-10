package com.github.nantianba.tools.streams.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public interface CollectOperator<T> {

    Set<T> toSet();

    Set<T> toSet(Supplier<Set<T>> supplier);


    List<T> toList();

    List<T> toList(Supplier<List<T>> supplier);

    ArrayList<T> toArrayList();


    /**
     * key must be unique,or an exception will be thrown
     */
    <K, V> Map<K, V> toMap(Function<T, K> keyMapper, Function<T, V> valueMapper);

    <K, V> Map<K, List<V>> groupBy(Function<T, K> keyMapper);

}
