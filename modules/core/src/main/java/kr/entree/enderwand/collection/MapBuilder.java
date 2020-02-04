package kr.entree.enderwand.collection;

import java.util.Map;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
public class MapBuilder<K, V> {
    private final Map<K, V> map;

    public MapBuilder(Map<K, V> map) {
        this.map = map;
    }

    public MapBuilder<K, V> put(K key, V value) {
        this.map.put(key, value);
        return this;
    }

    public Map<K, V> build() {
        return map;
    }
}
