package com.lacomanda.backend.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class RateLimiterService {

    private final ConcurrentMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    public boolean permitir(String clave) {
        Bucket bucket = buckets.computeIfAbsent(clave, k -> crearBucket());
        return bucket.tryConsume(1);
    }

    private Bucket crearBucket() {
        Bandwidth limite = Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1)));
        return Bucket.builder().addLimit(limite).build();
    }
}
