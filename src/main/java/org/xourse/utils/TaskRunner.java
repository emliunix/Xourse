package org.xourse.utils;

import java.util.function.Function;

/**
 * Created by Liu Yuhui on 2015/12/7.
 */
public class TaskRunner<R> {
    public R run(TaskRunner.Task<R> t, Function<Exception, R> excHandler) {
        try {
            return t.run();
        } catch (Exception e) {
            return excHandler.apply(e);
        }
    }

    public interface Task<R> {
        R run();
    }
}
