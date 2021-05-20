package com.github.nantianba.tools.console.tableprinter;

public interface BorderSetting {
    default char vertical() {
        return '|';
    }

    default char horizontal() {
        return '_';
    }
}
