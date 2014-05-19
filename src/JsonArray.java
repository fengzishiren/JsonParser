import java.util.ArrayList;
import java.util.List;

/**
 * @(#)JsonArray.java, 2014年5月19日.
 *
 * Copyright 2014 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**
 *
 * @author hzzhenglh
 * 2014年5月19日
 */
public class JsonArray {
    private List<Object> arrays = new ArrayList<Object>();

    public int length() {
        return arrays.size();
    }

    public Object get(int index) {
        try {
            return arrays.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void add(Object o) {
        arrays.add(o);
    }

    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return arrays.toString();
    }
}
