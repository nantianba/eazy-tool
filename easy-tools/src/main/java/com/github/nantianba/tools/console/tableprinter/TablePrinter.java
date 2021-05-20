package com.github.nantianba.tools.console.tableprinter;

import com.github.nantianba.tools.console.GridTable;

public class TablePrinter {
    private final GridTable gridTable;
    private final Align align;

    public TablePrinter(GridTable gridTable, Align align) {
        this.gridTable = gridTable;
        this.align = align;
        
        init();
    }

    private void init() {
        // TODO: 2021/5/20  
    }
}
