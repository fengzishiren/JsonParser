import static org.junit.Assert.*;

import org.junit.Test;

import com.netease.json.JSON;
import com.netease.json.JsonArray;
import com.netease.json.JsonObject;
import com.netease.json.Log;

/**
 * @(#)JsonClientTest.java, 2014年5月20日.
 *
 * Copyright 2014 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**
 *
 * @author hzzhenglh
 * 2014年5月20日
 */
public class JsonClientTest {
    /**
     * 所有结果均用info级别打印 
     */
    @Test
    public void testFoundation() {
        String text = "{\"name\": \"hzzhenglh\"}";
        JsonObject jo = (JsonObject) JSON.fromString(text);

        Log.d("------------------------------------------");
        Object object = jo.get("name");
        Log.i(object);

        text = "{" + "\"name\": \"hzzhenglh\"," + "\"hobby\": [" + "\"lanqiu\"," + "            \"yumaoqiu\"" + ", null]}";
        Log.d(text);

        jo = (JsonObject) JSON.fromString(text);
        Log.d("------------------------------------------");
        JsonArray ja = (JsonArray) jo.get("hobby");
        Log.i(ja);
        object = jo.get("name");
        Log.i(object);

        text = "[\"郑林海\", \"hzzhenglh@corpnetease.com\", 23]";
        Log.d(text);

        ja = (JsonArray) JSON.fromString(text);
        Log.i(ja);

        Log.e("END!");
    }

}
