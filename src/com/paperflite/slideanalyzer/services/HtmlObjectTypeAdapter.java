package com.paperflite.slideanalyzer.services;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.paperflite.slideanalyzer.services.impl.HtmlObject;

import java.io.IOException;

public class HtmlObjectTypeAdapter extends TypeAdapter<HtmlObject> {

    @Override
    public void write(JsonWriter out, HtmlObject htmlObject) throws IOException {
        out.beginObject();
        out.name("htmlContent").value(htmlObject.getHtmlContent());
        out.endObject();
    }

    @Override
    public HtmlObject read(JsonReader in) throws IOException {
        // Not used in this example
        return null;
    }
}

