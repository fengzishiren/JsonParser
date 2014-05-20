import static org.junit.Assert.*;

import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.netease.json.JsonUtil;
import com.netease.json.Log;
/**
 *
 * @author hzzhenglh
 * 2014年5月20日
 */
public class JsonUtilTest {
    @Test
    public void testIsPrimitive() throws Exception {
        boolean result;
        int a = 10;
        result = JsonUtil.isPrimitive(a);
        assertTrue(result);

        byte b = 3;
        result = JsonUtil.isPrimitive(b);
        assertTrue(result);

        char c = 'a';
        result = JsonUtil.isPrimitive(c);
        assertTrue(result);

        float f = 2.22f;
        result = JsonUtil.isPrimitive(f);
        assertTrue(result);

        double d = 2323.3332323;
        result = JsonUtil.isPrimitive(d);
        assertTrue(result);

        long l = System.currentTimeMillis();
        result = JsonUtil.isPrimitive(l);
        assertTrue(result);

    }

    @Test
    public void testToJson() throws Exception {

        @SuppressWarnings("unused")
        List<Data> list = Arrays.asList(new Data(), new Data());
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", "hzzhenglh");
        map.put("test", new Data());
        Log.d(JsonUtil.toJson(map));
    }

    @Test
    public void testRead() throws Exception {

        String s = JsonUtil.read(new FileReader("testsmall.json"));
        Log.i(s);
    }
}

class Data {
    String name = "hzzhenglh";

    int age = 22;

    Object data = new OperationResult();
}

class OperationResult {
    int status = 0;

    String msg = "OK";

    Object attach = null;

    Boolean success = Boolean.TRUE;

    boolean permit = true;

    byte a = 23;

    double money = 2.333333;

    Long l = System.currentTimeMillis();
}