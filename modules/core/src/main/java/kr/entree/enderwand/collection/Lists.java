package kr.entree.enderwand.collection;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
@UtilityClass
public class Lists {
    @SafeVarargs
    public static <T> List<T> of(T... values) {
        if (values.length == 0) {
            return Collections.emptyList();
        } else if (values.length == 1) {
            return Collections.singletonList(values[0]);
        }
        return new ArrayList<>(Arrays.asList(values));
    }
}
