package com.netease.json;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JSON {

    private static Parser parser = new Parser();

    public static Object fromString(String json) {
        return parser.parse(json);
    }

    public Object fromString(Reader reader) throws IOException {
        return parser.parse(readJsonFromFile(reader));
    }

    public static String toJson(Object javaObject) {
        return JsonUtil.toJson(javaObject);
    }

    private static final String readJsonFromFile(Reader _reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(_reader);

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(JsonUtil.NEW_LINE);
            }
        } finally {
            if (reader != null)
                reader.close();

        }
        return sb.toString();
    }
}