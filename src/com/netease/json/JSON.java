package com.netease.json;
import java.io.IOException;
import java.io.Reader;

public class JSON {

    private static Parser parser = new Parser();

    public static Object fromString(String json) {
        return parser.parse(json);
    }

    public Object fromString(Reader reader) throws IOException {
        return parser.parse(JsonUtil.read(reader));
    }

    public static String toJson(Object javaObject) {
        return JsonUtil.toJson(javaObject);
    }

}