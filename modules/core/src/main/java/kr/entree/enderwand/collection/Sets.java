package kr.entree.enderwand.collection;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
@UtilityClass
public class Sets {
    public static <T> Set<T> of(T... values) {
        return new HashSet<>(Arrays.asList(values));
    }
}
