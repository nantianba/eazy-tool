package com.github.nantianba.tools.console.tableprinter;

import com.github.nantianba.tools.console.GridTable;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TablePrinterTest {

    @Test
    public void write() {
        final List<A> source = Stream.generate(A::new)
                .limit(15)
                .collect(Collectors.toList());

        GridTable.from(source).printer(PrintSetting.builder()
                .align(Align.Left)
                .build())
                .write(new PrintWriter(System.out));
    }

    @Test
    public void testEmpty() {
        GridTable.empty().printer(PrintSetting.builder()
                .align(Align.Left)
                .build())
                .write(new PrintWriter(System.out));
    }

    @Test
    public void write2() {
        final List<A> source = Stream.generate(() -> {
            final A a = new A();
            a.testAString = "";
            a.testBString = "";
            a.testCString = "";
            a.testCStringLong = "";
            a.testDStringLong = "";
            return a;
        })
                .limit(15)
                .collect(Collectors.toList());
        GridTable.from(source)
                .printer(PrintSetting.defaultSetting())
                .write(new PrintWriter(System.out));
    }

    static class A {
        String testAString = "43t24rv54c51345c=2.34r24";
        String testBString = "44r24";
        String testCString = "44r24";
        String testCStringLong = null;
        String testDStringLong = "sdfasdfa sdfdfasdfwae4fda fdsafsdfadfasdfasdfas drq234r34c ";
    }
}