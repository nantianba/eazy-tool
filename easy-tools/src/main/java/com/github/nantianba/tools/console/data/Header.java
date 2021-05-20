package com.github.nantianba.tools.console.data;

import com.github.nantianba.tools.console.tableprinter.Align;

public class Header extends Cell<String> {

    public Header(String title, Align align) {
        super(title);
    }

    @Override
    public char horizontal() {
        return '=';
    }
}
