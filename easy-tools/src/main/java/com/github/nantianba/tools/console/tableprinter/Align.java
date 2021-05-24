package com.github.nantianba.tools.console.tableprinter;

public enum Align {
    Left {
        @Override
        public void addPadding(StringBuilder appender, String content, int paddingCount, char paddingChar) {
            appender.append(content);
            for (int i = 0; i < paddingCount; i++) {
                appender.append(paddingChar);
            }
        }
    },
    Center {
        @Override
        public void addPadding(StringBuilder appender, String content, int paddingCount, char paddingChar) {
            final int leftPadding = paddingCount / 2;
            for (int i = 0; i < leftPadding; i++) {
                appender.append(paddingChar);
            }
            appender.append(content);
            for (int i = 0; i < paddingCount - leftPadding; i++) {
                appender.append(paddingChar);
            }
        }
    },
    Right {
        @Override
        public void addPadding(StringBuilder appender, String content, int paddingCount, char paddingChar) {
            for (int i = 0; i < paddingCount; i++) {
                appender.append(paddingChar);
            }
            appender.append(content);
        }
    };

    public abstract void addPadding(StringBuilder appender, String content, int paddingCount, char paddingChar);
}
