package com.github.nantianba.tools.console;

import com.github.nantianba.tools.console.data.Cell;
import com.github.nantianba.tools.console.data.Line;
import org.junit.Test;

import java.util.*;

public class GridTableTest {

    @Test
    public void from() {
        final List<Integer> list = Arrays.asList(1, 2, 3);
        final GridTable table = GridTable.from(list);
        table.addHeaders(Line.single("test"));
        System.out.println("table.getHeaders().getCells().get(0).getContent() = " + table.getHeaders().getCells().get(0).getContent());

        for (Line line : table.getLines()) {
            System.out.println("line.getCells().get(0).getContent() = " + line.getCells().get(0).getContent());
        }
    }

    @Test
    public void fromMap() {
        final HashMap<Object, Object> map = new HashMap<>();
        map.put("test", "test");
        final GridTable from = GridTable.from(map);

        for (Line line : from.getLines()) {
            for (Cell cell : line.getCells()) {
                System.out.print(cell.getContent() + "\t");
            }

            System.out.println();
        }
    }

    @Test
    public void fromObj() {

        final LinkedList<Object> objects = new LinkedList<>();
        final A e = new A();
        e.a="a";
        e.b="b";
        e.c=1;
        e.d=true;
        objects.add(e);
        final GridTable table = GridTable.from(objects);
        for (Cell cell : table.getHeaders().getCells()) {
            System.out.print(cell.getContent() + "\t");
        }

        System.out.println();
        for (Line line : table.getLines()) {
            for (Cell cell : line.getCells()) {
                System.out.print(cell.getContent() + "\t");
            }

            System.out.println();
        }
    }

    static class A {
        private String a;
        private String b;
        private int c;
        private boolean d;
    }
}