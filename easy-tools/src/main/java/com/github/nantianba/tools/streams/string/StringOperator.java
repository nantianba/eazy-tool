package com.github.nantianba.tools.streams.string;

public interface StringOperator {
    String join();

    String join(CharSequence delimiter);

    String join(CharSequence delimiter, CharSequence prefix, CharSequence suffix);

}
