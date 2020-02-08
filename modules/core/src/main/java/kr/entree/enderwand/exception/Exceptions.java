package kr.entree.enderwand.exception;

import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.function.Supplier;

/**
 * Created by JunHyung Lim on 2020-02-08
 */
@UtilityClass
public class Exceptions {
    public static <T> Result<T> runCatching(Supplier<T> supplier) {
        val result = new Result<T>();
        try {
            result.setData(supplier.get());
        } catch (Exception ex) {
            result.setException(ex);
        }
        return result;
    }

    public static Result<Void> runCatching(Runnable runnable) {
        return runCatching(() -> {
            runnable.run();
            return null;
        });
    }
}
