package com.github.nantianba.tools.streams.collection;

import com.github.nantianba.tools.streams.CollectionStream;
import com.github.nantianba.tools.streams.type.StringStream;

import java.util.function.Function;

public interface StreamOperator<T> {
    CollectionStream<T> parallel();

    StringStream map(Function<T, String> mapper);


}
