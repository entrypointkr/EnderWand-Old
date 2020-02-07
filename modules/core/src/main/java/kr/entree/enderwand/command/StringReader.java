package kr.entree.enderwand.command;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.jetbrains.annotations.Nullable;

/**
 * Created by JunHyung Lim on 2020-02-07
 */
public class StringReader {
    private final String string;
    @Getter
    @Setter
    private int position = 0;
    private int next = -1;

    private StringReader(String string) {
        this.string = string;
    }

    public static StringReader of(String string) {
        return new StringReader(string);
    }

    public static StringReader ofEmpty() {
        return of("");
    }

    public boolean canRead() {
        if (position >= string.length()) {
            return false;
        }
        val spaceIndex = string.indexOf(' ', position);
        if (spaceIndex < 0) {
            next = string.length();
        } else {
            next = spaceIndex;
        }
        return true;
    }

    public String peek() {
        if (canRead()) {
            return string.substring(position, next);
        }
        throw new IndexOutOfBoundsException();
    }

    public String read() {
        val ret = peek();
        position = next + 1;
        return ret;
    }
}
