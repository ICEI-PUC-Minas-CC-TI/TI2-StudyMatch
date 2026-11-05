package com.studymatch.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

public class JsonUtil {
    private static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, type, ctx) -> new JsonPrimitive(src.toString()))
        .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, type, ctx) -> new JsonPrimitive(src.toString()))
        .registerTypeAdapter(LocalTime.class, (JsonSerializer<LocalTime>) (src, type, ctx) -> new JsonPrimitive(src.toString()))
        .create();

    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }
}