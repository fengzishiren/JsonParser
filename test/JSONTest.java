import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * @author lunatic 2014年7月26日
 */
public class JSONTest {
    /**
     * 所有结果均用info级别打印
     */
    @Test
    public void testFoundation() {
        String text = "{\"name\": \"lunatic\"}";
        JsonObject jo = (JsonObject) JSON.fromString(text);

        Log.d("------------------------------------------");
        Object object = jo.get("name");
        Log.i(object);

        text = "{" + "\"name\": \"lunatic\"," + "\"hobby\": ["
            + "\"lanqiu\"," + "            \"yumaoqiu\"" + ", null]}";
        Log.d(text);

        jo = (JsonObject) JSON.fromString(text);
        Log.d("------------------------------------------");
        JsonArray ja = (JsonArray) jo.get("hobby");
        Log.i(ja);
        object = jo.get("name");
        Log.i(object);

        text = "[\"郑林海\", \"lunaticlunatic\", 23]";
        Log.d(text);

        ja = (JsonArray) JSON.fromString(text);
        Log.i(ja);

        Log.w("END!");
    }

    @Test
    public void testFileJson() throws Exception {

        String json = UTest.readJsonFromFile("testmiddle.json");
        JsonObject jo = (JsonObject) JSON.fromString(json);
        Log.i("M: " + jo);

        json = UTest.readJsonFromFile("testbig.json");
        jo = (JsonObject) JSON.fromString(json);
        Log.i("B: " + jo);

        json = UTest.readJsonFromFile("test.json");
        jo = (JsonObject) JSON.fromString(json);
        Log.i("T: " + jo);
    }

    @Test
    public void testIEEEFloats() throws Exception {
        String json = "{\"test\":10.1E+4}";
        JsonObject jo = (JsonObject) JSON.fromString(json);
        Log.i("E:" + jo);
    }

    @Test
    public void testError() throws Exception {
        String json = "{\"test\":10.1E+4},";
        JsonObject jo = (JsonObject) JSON.fromString(json);
        Log.i("E:" + jo);
    }

    @Test
    public void testUnicode() throws Exception {
        String json = "{\"firstName\":\"\u5845\",\"lastName\":\"McLaughlin\",\"email\":\"aaaa\\\"bbbb\", \"age\":18, \"sex\":true, \"wife\":null}";
        JsonObject jo = (JsonObject) JSON.fromString(json);
        Log.i("Unicode:" + jo);
    }

    @Test
    public void testJo2Json() throws Exception {
        List<String> list = Arrays.asList("a", "b", "c", "d");
        String json = JSON.toJSON(list);
        Log.i("json: " + json);

        Map<Character, String> escapeMap = new HashMap<Character, String>();

        escapeMap.put('\"', "\\\"");
        escapeMap.put('\\', "\\\\");
        escapeMap.put('/', "\\/");
        escapeMap.put('\b', "\\b");
        escapeMap.put('\f', "\\f");
        escapeMap.put('\n', "\\n");
        escapeMap.put('\r', "\\r");
        escapeMap.put('\t', "\\t");
        String json2 = JSON.toJSON(escapeMap);
        Log.i("json2: " + json2);
    }
}
