package com.gamezone.jwt;

        import java.security.Provider;
        import java.security.Security;
        import java.util.Arrays;
        import java.util.concurrent.atomic.AtomicInteger;

public class ListProviders {
    public static void main(String[] args) {

        Provider[] providers = Security.getProviders();
        AtomicInteger atomicInteger = new AtomicInteger(1);
        Arrays.stream(providers)
                .map(provider ->
                        String.format("%d. Name: %s and Version: %s",
                                atomicInteger.getAndIncrement(),
                                provider.getName(),
                                provider.getVersion()))
                .forEach(System.out::println);
    }
}
