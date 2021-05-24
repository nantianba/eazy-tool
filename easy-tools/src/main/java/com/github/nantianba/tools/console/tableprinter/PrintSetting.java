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
    private final int minColWidth = 5;
    @Builder.Default
    private final int maxColWidth = 20;
    @Builder.Default
    private final int horizontalPadding = 1;
    @Builder.Default
    private final boolean showIndex = false;
    @Builder.Default
    private final boolean truncTooLong = false;
    @Builder.Default
    private final int truncLimitWidth = 100;

    public static PrintSetting defaultSetting() {
        return PrintSetting.builder().build();
    }

}