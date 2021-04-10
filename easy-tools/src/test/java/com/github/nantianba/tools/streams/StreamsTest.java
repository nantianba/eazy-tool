package com.github.nantianba.tools.streams;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

public class StreamsTest {

    @Test
    public void name() {
        ConcurrentLinkedDeque<Integer> deque = Arrays.asList(1, 2, 3)
                .stream()
                .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));

        System.out.println(deque);
    }
}