package com.paperflite.slideanalyzer.services;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.paperflite.slideanalyzer.services.impl.HtmlObject;

public class HtmlObjectSerializer implements JsonSerializer<HtmlObject> {
    @Override
    public JsonElement serialize(HtmlObject htmlObj, Type typeOfSrc, JsonSerializationContext context) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String htmlContent = htmlObj.getHtmlContent();
        return gson.toJsonTree(new HtmlObject(htmlContent));
    }
}
