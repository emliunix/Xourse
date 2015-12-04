package org.xourse.test.utils;

import org.junit.Test;
import org.xourse.utils.UserIdBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * 测试用例
 * TODO：添加异常抛出的测试
 * Created by Liu Yuhui on 2015/11/25.
 */
public class UserIdBuilderTest {

    @Test
    public void testPaddedDigitString() {
        try {
            Class clazz = UserIdBuilder.class;
            Method method = clazz.getDeclaredMethod("genPaddedDigitString", Long.TYPE, Integer.TYPE);
            method.setAccessible(true);
            Object result = method.invoke(null, Long.valueOf((long) 4), Integer.valueOf(4));
            assertEquals("0004", result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testStudentIdBuilder() {
        String stuId = UserIdBuilder.studentId(23)
                .year(2013)
                .major(30)
                .department(40)
                .build();
        assertEquals("201300400030023", stuId);
    }
}
