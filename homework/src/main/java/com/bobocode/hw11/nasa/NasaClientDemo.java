package com.bobocode.hw11.nasa;

import com.bobocode.hw11.nasa.model.Photo;

import java.net.http.HttpClient;
import java.util.Optional;
import java.util.function.Supplier;

public class NasaClientDemo {

    public static void main(String[] args) {
//        enable logging for HTTP client
//        System.setProperty("jdk.httpclient.HttpClient.log", "requests,response,headers");

        var httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        NasaClient nasaClient = new NasaClient(httpClient);

        log(nasaClient::findLargestPhotoAsync, "-----Finding largest photo asynchronously-----");
        System.out.println();
        log(nasaClient::findLargestPhoto, "-----Finding largest photo synchronously-----");
    }

    static void log(Supplier<Optional<Photo>> requestSupplier, String message) {
        System.out.println(message);

        long start = System.currentTimeMillis();
        var largestPhoto = requestSupplier.get();

        largestPhoto
                .ifPresent(photo -> {
                    System.out.println("Id: " + photo.getId());
                    System.out.println("URL: " + photo.getImgSrc());
                    System.out.println("Size: " + photo.getSize());
                });

        System.out.println("Time elapsed: " + (System.currentTimeMillis() - start));
    }
}
