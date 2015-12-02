package org.xourse;

/**
 * Access denied
 * Created by Liu Yuhui on 2015/12/2.
 */
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super("Access denied");
    }

    public AccessDeniedException(String message) {
        super(message);
    }
}
