package com.github.nantianba.tools.collections.impl;

import com.github.nantianba.tools.collections.Index;
import com.github.nantianba.tools.collections.MultiIndicesMap;
import com.github.nantianba.tools.collections.excception.MultiIndicesMapException;

import java.util.*;
import java.util.stream.Collectors;

public class DefaultMultiIndicesMap<T> implements MultiIndicesMap<T> {
    private final List<T> rawData;
    private final Map<Index<T, ?>, Map<Object, List<T>>> dataMapper = new HashMap<>();
    private final Map<Object, List<T>> EMPTY = new HashMap<>();


    public DefaultMultiIndicesMap(Collection<T> rawData, Index<T, ?>... indices) {
        if (rawData == null) {
            throw new MultiIndicesMapException("raw data is null");
        }
        if (indices.length == 0) {
            throw new MultiIndicesMapException("no indices defined");
        }
        this.rawData = new LinkedList<>(rawData);

        for (Index<T, ?> index : indices) {
            Map<Object, List<T>> collect = rawData.stream()
                    .collect(Collectors.groupingBy(t -> Optional.ofNullable(index.ref().apply(t))));

            dataMapper.put(index, collect);
        }
    }

    @Override
    public <K> List<T> get(Index<T, K> index, K key) {
        return dataMapper.getOrDefault(index, EMPTY)
                .get(Optional.ofNullable(key));
    }

    @Override
    public <K> T getFirst(Index<T, K> index, K key) {
        List<T> list = get(index, key);

        return list != null && list.size() > 0
                ? list.get(0)
                : null;
    }

    @Override
    public <K> boolean contains(Index<T, K> index, K key) {
        return dataMapper.containsKey(index)
                && dataMapper.get(index).containsKey(Optional.ofNullable(key));
    }

    @Override
    public int size() {
        return rawData.size();
    }

    @Override
    public List<T> getRawData() {
        return rawData;
    }
}
