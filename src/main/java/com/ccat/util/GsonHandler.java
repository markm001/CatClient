package com.ccat.util;

import com.ccat.util.serializer.PairSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Map;

public class GsonHandler {
    private static GsonHandler INSTANCE;
    private static Gson gson;

    private static final Map<Type, JsonSerializer<?>> types = Map.of(
            Pair.class, new PairSerializer<>()
    );

    private GsonHandler() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        types.forEach(gsonBuilder::registerTypeAdapter);

        gson = gsonBuilder.create();
    }

    public String serialize(Object o) {
        return gson.toJson(o, o.getClass());
    }
    public JsonObject serialize(String s) {
        return gson.fromJson(s, JsonObject.class);
    }


    public <T> T deserialize(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }
    public <T> T deserialize(JsonObject json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }


    public static GsonHandler getInstance() {
        if(INSTANCE == null) INSTANCE = new GsonHandler();

        return INSTANCE;
    }
}
