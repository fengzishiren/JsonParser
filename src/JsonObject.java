import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @(#)JsonObject.java, 2014年5月19日.
 *
 * Copyright 2014 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**
 *
 * @author hzzhenglh
 * 2014年5月19日
 */
public class JsonObject {
    private Map<Object, Object> properties = new HashMap<Object, Object>();

    public Object get(String key) {
        return properties.get(key);
    }

    public void put(Object key, Object val) {
        properties.put(key, val);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();
        Set<Entry<Object, Object>> entrySet = properties.entrySet();
        for (Map.Entry s : entrySet) {
            sb.append(s.getKey() + " = " + s.getValue() + ",");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}
