package com.github.nantianba.tools.console.tableprinter;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class PrintSetting {
    @Builder.Default
    private final Align align = Align.Center;
    @Builder.Default
    private final int maxColWidth = 20;
    @Builder.Default
    private final int minColWidth = 10;
    @Builder.Default
    private final boolean truncTooLong = false;
    @Builder.Default
    private final int truncLimitWidth = 100;
    @Builder.Default
    private final int horizontalPadding = 1;

    public static PrintSetting defaultSetting() {
        return PrintSetting.builder().build();
    }

}