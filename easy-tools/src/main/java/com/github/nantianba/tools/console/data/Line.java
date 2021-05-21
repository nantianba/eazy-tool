package com.github.nantianba.tools.console.data;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Line {
    @Getter
    private final List<? extends Cell> cells;

    private Line(List<? extends Cell> cells) {
        this.cells = cells;
    }

    public static Line of(List<? extends Cell> cells) {
        return new Line(cells);
    }

    public static Line ofData(Object... objects) {
        return of(Arrays.stream(objects).map(Cell::of).collect(Collectors.toList()));
    }
    public static Line ofData(Collection<?> objects) {
        return of(objects.stream().map(Cell::of).collect(Collectors.toList()));
    }

    public static Line single(Object obj) {
        return ofData(Collections.singletonList(obj));
    }
}
