package com.ccat.executor;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Call wrapper
 * @param <T> Type
 */
public class Call<T> {
    private final Supplier<T> execution;

    public Call(Supplier<T> execution) {
        this.execution = execution;
    }

    public T execute() {
        return execution.get();
    }

    public void enqueue(Consumer<T> onSuccess, Consumer<Throwable> onFailure) {
        new Thread(() -> {
            try {
                T result = execution.get();
                onSuccess.accept(result);
            } catch (Throwable t) {
                onFailure.accept(t);
            }
        }).start();
    }
}
