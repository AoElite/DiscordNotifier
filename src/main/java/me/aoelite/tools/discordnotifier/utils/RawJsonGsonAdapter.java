package me.aoelite.tools.discordnotifier.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class RawJsonGsonAdapter extends TypeAdapter<String> {

    @Override
    public void write(final JsonWriter out, final String value) throws IOException {
        out.jsonValue(value);
    }

    @Override
    public String read(final JsonReader in) throws IOException {
        return null; // Not supported
    }
}
