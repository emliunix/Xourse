package org.xourse.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liu Yuhui on 2015/11/24.
 */
public class MessageUtils {
    public static final Boolean STATUS_SUCCESS = Boolean.TRUE;
    public static final Boolean STATUS_FAIL = Boolean.FALSE;

    public static Map<String, Object> success(String msg) {
        Map<String, Object> m = new HashMap<>();
        m.put("status", STATUS_SUCCESS);
        m.put("msg", msg);
        return m;
    }

    public static Map<String, Object> fail(String msg) {
        Map<String, Object> m = new HashMap<>();
        m.put("status", STATUS_FAIL);
        m.put("msg", msg);
        return m;
    }

}
