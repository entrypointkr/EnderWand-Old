package kr.entree.enderwand.collection;


import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
public class Reader<T> {
    private final List<T> list;
    private int pos = 0;

    public static <T> Reader<T> of(List<T> list) {
        return new Reader<>(list);
    }

    @SafeVarargs
    public static <T> Reader<T> of(T... values) {
        return of(Lists.of(values));
    }

    private Reader(List<T> list) {
        this.list = list;
    }

    public int position() {
        return pos;
    }

    public boolean canRead() {
        return list.size() > pos;
    }

    public T peek() {
        return list.get(pos);
    }

    @Nullable
    public T peekOrNull() {
        return canRead() ? peek() : null;
    }


}
