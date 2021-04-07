package com.github.nantianba.tools.multimap.impl;

import com.github.nantianba.tools.multimap.Index;
import com.github.nantianba.tools.multimap.MultiIndicesMap;
import io.reactivex.Observable;
import lombok.Data;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DefaultMultiIndicesMapTest {

    public static final Index<A, String> STRING_INDEX = Index.of(A::getKeyStr);
    public static final Index<A, Integer> INTEGER_INDEX = Index.of(A::getKeyInt);
    public static final Index<A, Long> LONG_INDEX = Index.of(A::getKeyLong);

    @Test
    public void test() {
        LinkedList<A> list = new LinkedList<>();

        int listSize = 100;

        for (int i = 0; i < listSize; i++) {
            A e = new A();

            e.setKeyInt(new Random().nextInt(10));
            e.setKeyLong(new Random().nextInt(10));
            e.setKeyStr(String.valueOf(new Random().nextInt(10)));

            list.add(e);
        }

        MultiIndicesMap<A> map = MultiIndicesMap.of(list, STRING_INDEX, INTEGER_INDEX, LONG_INDEX);

        assert map.size() == listSize;

        assert Observable.range(0, 10)
                .map(i -> map.get(INTEGER_INDEX, i))
                .map(List::size)
                .reduce(Integer::sum)
                .blockingGet() == listSize;

        A e1 = new A();

        e1.setKeyInt(129);
        e1.setKeyLong(129);
        e1.setKeyStr("129");

        list.add(e1);

        MultiIndicesMap<A> map2 = MultiIndicesMap.of(list, STRING_INDEX, INTEGER_INDEX, LONG_INDEX);

        assert !map2.contains(STRING_INDEX, "100");
        assert map2.contains(STRING_INDEX, "129");
        assert map2.contains(LONG_INDEX, 129L);
        assert map2.contains(INTEGER_INDEX, 129);

        assert map2.get(STRING_INDEX, "129") != null;
        List<A> list129 = map2.get(STRING_INDEX, "129");
        System.out.println("map2.get(stringIndex, \"129\").get(0) = " + list129.get(0));

        assert map2.getFirst(INTEGER_INDEX, 129) == list129.get(0);

        System.out.println("map2.getRawData().size() = " + map2.getRawData().size());
    }

    @Data
    static class A {
        private String keyStr;
        private int keyInt;
        private long keyLong;
    }
}