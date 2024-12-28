package com.ccat.util;

public record Pair<K,V>(
        K key,
        V value
) {
    public static <K,V> Pair<K,V> of(K k1, V v1) {
        return new Pair<>(k1, v1);
    }
}
