package kr.entree.enderwand.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by JunHyung Lim on 2020-02-08
 */
public class Result<T> {
    @Getter
    @Setter
    private T data = null;
    @Getter
    @Setter
    private Exception exception = null;

    public Result<T> onSuccess(Consumer<T> receiver) {
        receiver.accept(data);
        return this;
    }

    public Result<T> onFailure(Consumer<Exception> receiver) {
        receiver.accept(exception);
        return this;
    }

    public void onFailureThrow(Function<Exception, RuntimeException> mapper) {
        if (exception != null) {
            throw mapper.apply(getException());
        }
    }
}
