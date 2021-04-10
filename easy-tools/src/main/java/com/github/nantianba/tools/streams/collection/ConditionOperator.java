package com.github.nantianba.tools.streams.collection;

import java.util.function.Predicate;

/**
 *
 * @param <T>
 */
public interface ConditionOperator<T> {

    boolean all(Predicate<T> condition);

    boolean none(Predicate<T> condition);

    boolean any(Predicate<T> condition);

}
