package com.github.nantianba.tools.console.data;

import java.util.List;

public class Line {
    private final List<? extends Cell> cells;

    private Line(List<? extends Cell> cells) {
        this.cells = cells;
    }

    public static Line of(List<? extends Cell> cells) {
        return new Line(cells);
    }
}
