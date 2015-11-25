package org.xourse.utils;

import sun.plugin.dom.exception.InvalidStateException;

import java.text.NumberFormat;

/**
 * Build Student or Teacher ID
 * Created by Liu Yuhui on 2015/11/25.
 */
public class UserIdBuilder {

    public static StudentIdBuilder studentId(String id) {
        return new StudentIdBuilder(id);
    }

    public static StudentIdBuilder studentId(int id) {
        return new StudentIdBuilder(id);
    }

    public static TeacherIdBuilder teacherId(String id) {
        return new TeacherIdBuilder(id);
    }

    private static String genPaddedDigitString(long num, int len) {
        if(num < 0) throw new IllegalArgumentException("major[" + num + "] must be positive");
        String val = Long.toString(num);
        if(val.length() > len) throw new IllegalArgumentException("major[" + num + "] out of range");
        char[] chars = new char[len];
        System.arraycopy(val.toCharArray(), 0, chars, len - val.length(), val.length());
        for(int i = 0; i < chars.length - val.length(); ++i) {
            chars[i] = '0';
        }
        return String.valueOf(chars);
    }

    public static class StudentIdBuilder {
        private String id;
        private String majorId;
        private String departmentId;
        private String year;

        public StudentIdBuilder(String id) {
            this.id = id;
        }

        public StudentIdBuilder(int id) {
            this.id = genPaddedDigitString(id, 3);
        }

        public StudentIdBuilder major(String major) {
            this.majorId = major;
            return this;
        }

        public StudentIdBuilder major(long major) {
            this.majorId = genPaddedDigitString(major, 4);
            return this;
        }

        public StudentIdBuilder department(String department) {
            this.departmentId = department;
            return this;
        }

        public StudentIdBuilder department(long department) {
            this.departmentId = genPaddedDigitString(department, 4);
            return this;
        }

        public StudentIdBuilder year(String year) {
            this.year = year;
            return this;
        }

        public StudentIdBuilder year(int year) {
            this.year = genPaddedDigitString(year, 4);
            return this;
        }

        public String build() {
            return year + departmentId + majorId + id;
        }
    }

    public static class TeacherIdBuilder {
        private String id;
        private String departmentId;
        private String year;

        public TeacherIdBuilder(String id) {
            this.id = id;
        }

        public String build() {
            return year + departmentId + id;
        }
    }
}
