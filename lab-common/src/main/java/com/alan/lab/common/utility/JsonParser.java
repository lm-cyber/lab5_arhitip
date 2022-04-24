package com.alan.lab.common.utility;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.utility.json.LocalDateTimeDeserializer;
import com.alan.lab.common.utility.json.LocalDateTimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.PriorityQueue;

public final class JsonParser {
    private JsonParser() {
    }
    public static PriorityQueue<Person> toData(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .setPrettyPrinting().create();
        Type typeToken = new TypeToken<PriorityQueue<Person>>() {
        }.getType();
        return gson.fromJson(json.trim(), typeToken);
    }

    public static String toJson(PriorityQueue<Person> people) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .setPrettyPrinting().create();
        return gson.toJson(people);
    }
}
