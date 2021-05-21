package com.github.nantianba.tools.console.tableprinter;

import com.github.nantianba.tools.console.GridTable;
import com.github.nantianba.tools.console.data.Cell;
import com.github.nantianba.tools.console.data.Line;
import lombok.var;

import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TablePrinter {
    private final Line headers;
    private final List<Line> data;
    private final int colNum;
    private final PrintSetting printSetting;
    private final ArrayList<Integer> colWidthMemo;
    private final Map<Class, TypeWriter> typeWriters = new HashMap<>();


    public TablePrinter(GridTable gridTable, PrintSetting printSetting) {
        this.headers = gridTable.getHeaders();
        this.data = gridTable.getLines();
        this.printSetting = printSetting;
        this.colNum = this.data.get(0).getCells().size();

        this.colWidthMemo = IntStream.range(0, colNum).map(i -> 0)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));

        init();
    }

    public <T> TablePrinter addTypeWriter(Class<T> clazz, TypeWriter<T> typeWriter) {
        this.typeWriters.put(clazz, typeWriter);

        return this;
    }

    public void write(PrintWriter writer) {
        final boolean noHeader = headers == null;
        if (!noHeader) {
            writer.println(buildHeaderBorder(true));
            writer.println(buildLine(headers));
            writer.println(buildHeaderBorder(false));
        } else {
            writer.println(buildRolBorder(false, true));
        }

        writer.println(data.stream()
                .map(this::buildLine)
                .collect(Collectors.joining("\n" + buildRolBorder(false, false) + "\n",
                        "",
                        "\n" + buildRolBorder(true, false))
                )
        );

        writer.flush();
    }

    private String buildLine(Line line) {
        var cols = line.getCells();

        List<String> contents = new LinkedList<>();
        for (Cell col : cols) {
            final TypeWriter typeWriter;
            final String content;

            if (col.getData() != null && (typeWriter = getTypeWriter(col.getData())) != null) {
                content = typeWriter.convert(col.getData());
            } else {
                content = col.getContent();
            }

            if (printSetting.isTruncTooLong() && content.length() > printSetting.getTruncLimitWidth()) {
                contents.add(content.substring(0, printSetting.getTruncLimitWidth() - 3) + "...");
            } else {
                contents.add(content);
            }
        }


        int maxContentLength = contents.stream().mapToInt(String::length).max().getAsInt();

        if (printSetting.isTruncTooLong() && printSetting.getTruncLimitWidth() < maxContentLength) {
            maxContentLength = printSetting.getTruncLimitWidth();
        }

        int lineNum = maxContentLength / printSetting.getMaxColWidth() + (maxContentLength % printSetting.getMaxColWidth() == 0 ? 0 : 1);

        lineNum = Integer.max(lineNum, 1);

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < lineNum; i++) {
            final Iterator<Integer> colWidthIter = colWidthMemo.iterator();
            final Iterator<String> dataIter = contents.iterator();

            StringBuilder lineBuilder = new StringBuilder("│");
            while (colWidthIter.hasNext()) {
                int colWidth = colWidthIter.next();
                final String content = dataIter.next();

                colWidth = Integer.max(printSetting.getMinColWidth(), colWidth);
                colWidth = Integer.min(colWidth, printSetting.getMaxColWidth());

                int offset = i * printSetting.getMaxColWidth();
                int maxPosition = offset + colWidth;

                String contentThis = content.length() <= offset
                        ? ""
                        :
                        (content.length() < maxPosition
                                ? content.substring(offset)
                                : content.substring(offset, maxPosition));

                final int length = contentThis.length();

                lineBuilder.append(repeat(' ', printSetting.getHorizontalPadding()));

                switch (printSetting.getAlign()) {
                    case Left:
                        lineBuilder.append(contentThis).append(repeat(' ', colWidth - length));
                        break;
                    case Center:
                        int leftRepeatNum = (colWidth - length) / 2;
                        int rightRepeatNum = colWidth - length - leftRepeatNum;
                        lineBuilder.append(repeat(' ', leftRepeatNum)).append(contentThis).append(repeat(' ', rightRepeatNum));
                        break;
                    case Right:
                        lineBuilder.append(repeat(' ', colWidth - length)).append(contentThis);
                        break;
                }

                lineBuilder.append(repeat(' ', printSetting.getHorizontalPadding()));

                lineBuilder.append("│");
            }
            builder.append(lineBuilder);

            if (lineNum > 1 && i != lineNum - 1) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    private TypeWriter getTypeWriter(Object obj) {
        final Class<?> objClass = obj.getClass();
        return typeWriters.entrySet().stream()
                .filter(c -> objClass.equals(c.getKey()) || c.getKey().isAssignableFrom(objClass))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    private String buildRolBorder(boolean bottom, boolean noHeader) {
        final Stream<String> stream = colWidthMemo.stream()
                .map(w -> getColWidth(w, printSetting))
                .map(w -> repeat('─', w + (printSetting.getHorizontalPadding() << 1)));

        if (bottom) {
            return stream.collect(Collectors.joining("┴", "└", "┘"));
        }
        if (noHeader) {
            return stream.collect(Collectors.joining("┬", "┌", "┐"));
        }
        return stream.collect(Collectors.joining("┼", "├", "┤"));
    }

    private String buildHeaderBorder(boolean upper) {
        final Stream<String> stream = colWidthMemo.stream()
                .map(w -> getColWidth(w, printSetting))
                .map(w -> repeat('═', w + (printSetting.getHorizontalPadding() << 1)));

        return upper ? stream.collect(Collectors.joining("╤", "╒", "╕"))
                : stream.collect(Collectors.joining("╪", "╞", "╡"));
    }

    private int getColWidth(int w, PrintSetting printSetting) {
        final int ans = Integer.min(w, printSetting.getMaxColWidth());
        return Integer.max(printSetting.getMinColWidth(), ans);
    }

    private String repeat(char c, int length) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < length; i++) {
            s.append(c);
        }

        return s.toString();
    }

    private void checkLineWidth(Line line) {
        var iterator = line.getCells().iterator();
        int i = 0;
        while (iterator.hasNext()) {

            final int length = iterator.next().getContent().length();
            if (length > colWidthMemo.get(i)) {
                colWidthMemo.set(i, length);
            }

            i++;
        }
    }

    private void init() {
        if (headers != null) {
            checkLineWidth(headers);
        }

        for (Line line : data) {
            checkLineWidth(line);
        }
    }
}
