package com.github.nantianba.tools.console.data;

public class Header extends Cell<String> {

    private Header(String title) {
        super(title);
    }

    public static Header of(String val) {
        return new Header(val);
    }

}
