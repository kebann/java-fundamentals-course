package com.bobocode.hw11.nasa.utils;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@UtilityClass
public class CompletableFutureUtils {

    public <T> CompletableFuture<List<T>> allOf(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture =
                CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));

        return allDoneFuture.thenApply(v ->
                futures.stream().
                        map(CompletableFuture::join).
                        toList()
        );
    }
}
