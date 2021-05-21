package com.github.nantianba.tools.console;

import com.github.nantianba.tools.console.data.Header;
import com.github.nantianba.tools.console.data.Line;
import com.github.nantianba.tools.console.tableprinter.Align;
import com.github.nantianba.tools.console.tableprinter.PrintSetting;
import com.github.nantianba.tools.console.tableprinter.TablePrinter;
import lombok.Getter;
import lombok.var;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class GridTable {
    @Getter
    private Line headers;
    @Getter
    private final List<Line> lines;

    private static GridTable EMPTY = new GridTable(null, Collections.singletonList(Line.single("empty")));

    public static GridTable empty() {
        return EMPTY;
    }

    public void addHeaders(Line headers) {
        this.headers = headers;
    }

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

    public static GridTable from(Map<?, ?> map) {
        final List<Line> lines = new LinkedList<>();
        for (var p : map.entrySet()) {
            lines.add(Line.ofData(Arrays.asList(p.getKey(), p.getValue())));
        }
        return new GridTable(lines);
    }

    public static GridTable from(Collection<?> objects) {
        if (objects == null || objects.size() == 0) {
            return GridTable.empty();
        }

        final Object first = objects.stream().findFirst().get();

        if (first instanceof String || first instanceof Number || first.getClass().isPrimitive()) {
            final List<Line> lines = objects.stream()
                    .map(Line::single)
                    .collect(Collectors.toList());

            return new GridTable(null, lines);
        }

        final Field[] fields = first.getClass().getDeclaredFields();

        final List<Header> headers = Arrays.stream(fields)
                .map(val -> Header.of(val.getName()))
                .collect(Collectors.toList());
        final Line headerLine = Line.of(headers);

        final List<Line> lines = new LinkedList<>();

        for (Object object : objects) {
            final LinkedList<Object> datas = new LinkedList<>();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    datas.add(field.get(object));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            lines.add(Line.ofData(datas));
        }

        return new GridTable(headerLine, lines);
    }

    public TablePrinter printer(PrintSetting printSetting) {
        return new TablePrinter(this, printSetting);
    }


}