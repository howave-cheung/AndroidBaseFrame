package com.bobo.baseframe.network.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @ClassName IntegerTypeAdapter
 * @Description 一句话概括作用
 * @Date 2019/6/26
 * @History 2019/6/26 author: description:
 */
public class StringNullAdapter extends TypeAdapter<String> {
    @Override
    public String read(JsonReader reader) throws IOException {

        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return "";
        }
        return reader.nextString();
    }
    @Override
    public void write(JsonWriter writer, String value) throws IOException {

        if (value == null) {
            writer.nullValue();
            return;
        }
        writer.value(value);
    }
}
