package com.github.nantianba.tools.console.tableprinter;

import com.github.nantianba.tools.console.DataTable;
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
    private final int indexWidth;
    private final Map<Class, TypeWriter> typeWriters = new HashMap<>();


    public TablePrinter(DataTable dataTable, PrintSetting printSetting) {
        this.headers = dataTable.getHeaders();
        this.data = dataTable.getLines();
        this.printSetting = printSetting;
        this.colNum = this.data.get(0).getCells().size();
        this.indexWidth = printSetting.isShowIndex() ? String.valueOf(this.data.size()).length() : 0;

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
        if (noHeader) {
            writer.println(buildRolBorder(false, true));
        } else {
            writer.println(buildHeaderBorder(true));
            writer.println(buildLine(headers, ""));
            writer.println(buildHeaderBorder(false));
        }

        StringJoiner tableJointer = new StringJoiner(
                "\n" + buildRolBorder(false, false) + "\n",
                "",
                "\n" + buildRolBorder(true, false));

        for (int inex = 0, dataSize = data.size(); inex < dataSize; inex++) {
            tableJointer.add(buildLine(data.get(inex), inex));
        }

        writer.println(tableJointer);

        writer.flush();
    }

    private String buildLine(Line line, Object indexContent) {
        LinkedList<String> printContents = getPrintContent(line);

        if (printSetting.isShowIndex()) {
            printContents.addFirst(String.valueOf(indexContent));
        }

        int lineCount = getLineCount(printContents);

        StringBuilder builder = new StringBuilder();

        for (int lineNo = 0; lineNo < lineCount; lineNo++) {
            final Iterator<Integer> colMaxWidthIter = getColWidthMemo().iterator();
            final Iterator<String> dataIter = printContents.iterator();

            StringBuilder lineBuilder = new StringBuilder("│");
            while (colMaxWidthIter.hasNext()) {
                final String content = dataIter.next();

                int colMaxWidth = limitColWidth(colMaxWidthIter.next());
                int offset = lineNo * printSetting.getMaxColWidth();
                int maxPosition = offset + colMaxWidth;

                String contentThisLine;
                if (content.length() <= offset) {
                    contentThisLine = "";
                } else {
                    if (content.length() < maxPosition) {
                        contentThisLine = content.substring(offset);
                    } else {
                        contentThisLine = content.substring(offset, maxPosition);
                    }
                }

                final int length = contentThisLine.length();

                lineBuilder.append(repeat(' ', printSetting.getHorizontalPadding()));

                printSetting.getAlign().addPadding(lineBuilder, contentThisLine, colMaxWidth - length, ' ');

                lineBuilder.append(repeat(' ', printSetting.getHorizontalPadding()));
                lineBuilder.append("│");
            }
            builder.append(lineBuilder);

            if (lineCount > 1 && lineNo != lineCount - 1) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    private int getLineCount(List<String> contents) {
        int rolMaxPrintLength = contents.stream().mapToInt(String::length).max().getAsInt();

        if (printSetting.isTruncTooLong() && printSetting.getTruncLimitWidth() < rolMaxPrintLength) {
            rolMaxPrintLength = printSetting.getTruncLimitWidth();
        }

        int lineNum = rolMaxPrintLength / printSetting.getMaxColWidth() + (rolMaxPrintLength % printSetting.getMaxColWidth() == 0 ? 0 : 1);

        lineNum = Integer.max(lineNum, 1);
        return lineNum;
    }

    private LinkedList<String> getPrintContent(Line line) {
        LinkedList<String> contents = new LinkedList<>();

        for (Cell col : line.getCells()) {
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
        return contents;
    }

    private TypeWriter getTypeWriter(Object obj) {
        final Class<?> objClass = obj.getClass();
        return typeWriters.entrySet().stream()
                .filter(c -> objClass.equals(c.getKey()) || c.getKey().isAssignableFrom(objClass))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    private Stream<Integer> getColWidthMemo() {
        if (printSetting.isShowIndex()) {
            return Stream.concat(Stream.of(indexWidth), colWidthMemo.stream());
        }
        return colWidthMemo.stream();
    }

    private String buildHeaderBorder(boolean upper) {
        final Stream<String> stream = getColWidthMemo()
                .map(this::limitColWidth)
                .map(w -> repeat('═', w + (printSetting.getHorizontalPadding() << 1)));

        return upper ? stream.collect(Collectors.joining("╤", "╒", "╕"))
                : stream.collect(Collectors.joining("╪", "╞", "╡"));
    }

    private String buildRolBorder(boolean bottom, boolean noHeader) {
        final Stream<String> stream = getColWidthMemo()
                .map(this::limitColWidth)
                .map(w -> repeat('─', w + (printSetting.getHorizontalPadding() << 1)));

        if (bottom) {
            return stream.collect(Collectors.joining("┴", "└", "┘"));
        }
        if (noHeader) {
            return stream.collect(Collectors.joining("┬", "┌", "┐"));
        }
        return stream.collect(Collectors.joining("┼", "├", "┤"));
    }

    private int limitColWidth(int w) {
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

    private void findColumnMaxWidth(Line line) {
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
            findColumnMaxWidth(headers);
        }

        for (Line line : data) {
            findColumnMaxWidth(line);
        }
    }
}
