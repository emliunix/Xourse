package org.xourse.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Create Message Template
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

    public static Map<String, Object> success() {
        return success("success");
    }

    public static Map<String, Object> fail(String msg) {
        Map<String, Object> m = new HashMap<>();
        m.put("status", STATUS_FAIL);
        m.put("msg", msg);
        return m;
    }

    public static Map<String, Object> fail() {
        return fail("failed");
    }

}
