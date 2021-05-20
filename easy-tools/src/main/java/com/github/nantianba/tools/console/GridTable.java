package com.github.nantianba.tools.console;

import com.github.nantianba.tools.console.data.Cell;
import com.github.nantianba.tools.console.data.Line;
import com.github.nantianba.tools.console.tableprinter.Align;
import com.github.nantianba.tools.console.tableprinter.TablePrinter;
import lombok.Getter;
import lombok.With;
import lombok.var;

import java.util.*;

public class GridTable {
    @Getter
    @With
    private final Line headers;
    @Getter
    private final List<Line> lines;

    public boolean hasHeader() {
        return headers != null;
    }

    private GridTable(List<Line> lines) {
        this.lines = lines;
        this.headers = null;
    }

    private GridTable(Line headers, List<Line> lines) {
        this.headers = headers;

        this.lines = lines;
    }

    public static GridTable from(Collection<?> collection) {
        final LinkedList<Line> lines = new LinkedList<>();
        for (Object o : collection) {
            lines.add(Line.of(Collections.singletonList(Cell.of(o))));
        }
        return new GridTable(lines);
    }

    public static GridTable from(Map<?, ?> map) {
        final LinkedList<Line> lines = new LinkedList<>();
        for (var p : map.entrySet()) {
            lines.add(Line.of(Collections.singletonList(Cell.of(p.getKey()))));
        }
        return new GridTable(lines);
    }

    public static GridTable from(Object obj) {
        return null;
    }

    public TablePrinter printer(Align align) {
        return new TablePrinter(this, align);
    }
}
