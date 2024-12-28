package com.ccat.util.serializer;

import com.ccat.util.Pair;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class PairSerializer<K,V> implements JsonSerializer<Pair<K,V>> {
    @Override
    public JsonElement serialize(Pair<K,V> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(String.valueOf(src.key()), context.serialize(src.value()));
        return jsonObject;
    }
}
