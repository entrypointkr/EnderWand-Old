package kr.entree.enderwand.command;

import java.util.function.Function;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
public class Argument<T> {
    private final String name;
    private final Function<StringReader, T> parser;

    public Argument(String name, Function<StringReader, T> parser) {
        this.name = name;
        this.parser = parser;
    }

    public T parse(StringReader reader) {
        return parser.apply(reader);
    }
}
