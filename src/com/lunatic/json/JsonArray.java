package com.lunatic.json;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author hzzhenglh
 * 2014年5月19日
 */
public class JsonArray {
    private List<Object> array = new ArrayList<Object>();

    public int length() {
        return array.size();
    }

    public Object get(int index) {
        try {
            return array.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void add(Object o) {
        array.add(o);
    }

    /**
     * 
     *         Iterator<E> i = iterator();
     if (! i.hasNext())
         return "[]";

     StringBuilder sb = new StringBuilder();
     sb.append('[');
     for (;;) {
         E e = i.next();
         sb.append(e == this ? "(this Collection)" : e);
         if (! i.hasNext())
         return sb.append(']').toString();
         sb.append(", ");
     }
     }
     * 
     */
    @Override
    public String toString() {
        Iterator<Object> it = array.iterator();
        if (!it.hasNext())
            return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            Object e = it.next();
            if (e == null)
                sb.append("null");
            else if (JsonUtil.isPrimitive(e) || e instanceof JsonArray || e instanceof JsonObject)
                sb.append(e.toString());
            else
                sb.append('\"').append(e.toString()).append('\"');
            if (!it.hasNext())
                return sb.append(']').toString();
            sb.append(", ");
        }

    }

    public static void main(String[] args) {
        JsonArray ja = new JsonArray();
        ja.add("nihao");
        ja.add(Boolean.TRUE);
        System.out.println(ja);
        // boolean b = typeofObject(Integer.valueOf(99));
        // System.out.println(b);
    }
}
