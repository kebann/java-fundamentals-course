package com.bobocode.hw11.nasa.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.http.HttpResponse;
import java.util.function.Function;

///copy-paste from
// "https://gist.githubusercontent.com/billybong/0b2963c85912f4a2ee7b591dd85a93b6/raw/087428d6cfe7a4435a0b9ddfcb2f857b5a9c7bbf/JacksonBodyHandlers.java
public class JacksonBodyHandler {

    public static <T> HttpResponse.BodyHandler<T> asJson(Class<T> clazz) {
        return responseInfo -> subscriberFrom((bytes) -> new ObjectMapper().readValue(bytes, clazz));
    }

    private static <T> HttpResponse.BodySubscriber<T> subscriberFrom(IOFunction<byte[], T> ioFunction) {
        return HttpResponse.BodySubscribers.mapping(HttpResponse.BodySubscribers.ofByteArray(), rethrowRuntime(ioFunction));
    }

    @FunctionalInterface
    interface IOFunction<V, T> {
        T apply(V value) throws IOException;
    }

    private static <V, T> Function<V, T> rethrowRuntime(IOFunction<V, T> ioFunction) {
        return (V v) -> {
            try {
                return ioFunction.apply(v);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }
}