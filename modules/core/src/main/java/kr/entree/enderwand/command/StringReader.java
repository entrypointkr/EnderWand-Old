package kr.entree.enderwand.command;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by JunHyung Lim on 2020-02-07
 */
public class StringReader {
    private final String string;
    @Getter
    @Setter
    private int position = 0;

    private StringReader(String string) {
        this.string = string;
    }

    public static StringReader of(String string) {
        return new StringReader(string);
    }

    public static StringReader ofEmpty() {
        return of("");
    }
}
