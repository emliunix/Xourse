package org.xourse.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liu Yuhui on 2015/11/24.
 */
public class MessageUtils {
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_FAIL = "fail";

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
