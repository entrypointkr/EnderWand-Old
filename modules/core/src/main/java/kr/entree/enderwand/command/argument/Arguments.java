package kr.entree.enderwand.command.argument;

import lombok.experimental.UtilityClass;

/**
 * Created by JunHyung Lim on 2020-02-07
 */
@UtilityClass
public class Arguments {
    public static Argument<Integer> ints(String name) {
        return new Argument<>(name, reader -> Integer.parseInt(reader.read()));
    }

    public static Argument<Double> doubles(String name) {
        return new Argument<>(name, reader -> Double.parseDouble(reader.read()));
    }
}
