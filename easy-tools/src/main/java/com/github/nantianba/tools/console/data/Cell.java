package com.github.nantianba.tools.console.data;

import com.github.nantianba.tools.console.tableprinter.BorderSetting;
import lombok.Getter;

public class Cell<T> implements BorderSetting {
    @Getter
    private final T data;
    @Getter
    private final String content;

    protected Cell(T data) {
        this.data = data;
        this.content = data == null
                ? ""
                : data.toString();
    }

    public static <R> Cell<R> of(R val) {
        return new Cell<>(val);
    }

    public int length() {
        return content.length();
    }
}
