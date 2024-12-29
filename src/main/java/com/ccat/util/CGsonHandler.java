package com.ccat.util;

import com.ccat.util.serializer.PairSerializer;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Just a glorified Gson Class - same thing without having to import the dependency.
 * Allows pair Serialization.
 * Use for serializing & deserializing Objects.
 */
public class CGsonHandler {
    private static CGsonHandler INSTANCE;
    private static Gson gson;

    private static final Map<Type, JsonSerializer<?>> types = Map.of(
            Pair.class, new PairSerializer<>()
    );

    private CGsonHandler() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        types.forEach(gsonBuilder::registerTypeAdapter);

        gson = gsonBuilder.create();
    }

    /**
     * @return the GsonHandler Instance or create a new Instance before returning
     */
    public static CGsonHandler getInstance() {
        if(INSTANCE == null) INSTANCE = new CGsonHandler();

        return INSTANCE;
    }


    public boolean serializeNulls() {
        return gson.serializeNulls();
    }

    public boolean htmlSafe() {
        return gson.htmlSafe();
    }

    public <T> TypeAdapter<T> getAdapter(TypeToken<T> type) {
        return gson.getAdapter(type);
    }

    public <T> TypeAdapter<T> getAdapter(Class<T> type) {
        return gson.getAdapter(type);
    }

    public <T> TypeAdapter<T> getDelegateAdapter(TypeAdapterFactory skipPast, TypeToken<T> type) {
        return gson.getDelegateAdapter(skipPast, type);
    }

    public JsonElement toJsonTree(Object src) {
        return gson.toJsonTree(src);
    }

    public JsonElement toJsonTree(Object src, Type typeOfSrc) {
        return gson.toJsonTree(src, typeOfSrc);
    }

    public String toJson(Object src) {
        return gson.toJson(src);
    }

    public String toJson(Object src, Type typeOfSrc) {
        return gson.toJson(src, typeOfSrc);
    }

    public void toJson(Object src, Appendable writer) throws JsonIOException {
        gson.toJson(src, writer);
    }

    public void toJson(Object src, Type typeOfSrc, Appendable writer) throws JsonIOException {
        gson.toJson(src, typeOfSrc, writer);
    }

    public void toJson(Object src, Type typeOfSrc, JsonWriter writer) throws JsonIOException {
        gson.toJson(src, typeOfSrc, writer);
    }

    public String toJson(JsonElement jsonElement) {
        return gson.toJson(jsonElement);
    }

    public void toJson(JsonElement jsonElement, Appendable writer) throws JsonIOException {
        gson.toJson(jsonElement, writer);
    }

    public void toJson(JsonElement jsonElement, JsonWriter writer) throws JsonIOException {
        gson.toJson(jsonElement, writer);
    }

    public JsonWriter newJsonWriter(Writer writer) throws IOException {
        return gson.newJsonWriter(writer);
    }

    public JsonReader newJsonReader(Reader reader) {
        return gson.newJsonReader(reader);
    }

    public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        return gson.fromJson(json, classOfT);
    }

    public <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }

    public <T> T fromJson(String json, TypeToken<T> typeOfT) throws JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }

    public <T> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
        return gson.fromJson(json, classOfT);
    }

    public <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }

    public <T> T fromJson(Reader json, TypeToken<T> typeOfT) throws JsonIOException, JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }

    public <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return gson.fromJson(reader, typeOfT);
    }

    public <T> T fromJson(JsonReader reader, TypeToken<T> typeOfT) throws JsonIOException, JsonSyntaxException {
        return gson.fromJson(reader, typeOfT);
    }

    public <T> T fromJson(JsonElement json, Class<T> classOfT) throws JsonSyntaxException {
        return gson.fromJson(json, classOfT);
    }

    public <T> T fromJson(JsonElement json, Type typeOfT) throws JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }

    public <T> T fromJson(JsonElement json, TypeToken<T> typeOfT) throws JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }

    public String toString() {
        return gson.toString();
    }
}
