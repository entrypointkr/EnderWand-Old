package kr.entree.enderwand.collection;

import lombok.experimental.UtilityClass;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
@UtilityClass
public class Maps {
    public static <K, V> Map<K, V> of() {
        return new LinkedHashMap<>();
    }

    public static <K, V> MapBuilder<K, V> builder() {
        return new MapBuilder<>(of());
    }
}
