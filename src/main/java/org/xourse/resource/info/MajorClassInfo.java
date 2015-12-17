package org.xourse.resource.info;

import org.xourse.entity.Major;
import org.xourse.entity.MajorClass;

/**
 * Created by Liu Yuhui on 2015/12/2.
 */
public class MajorClassInfo {
    public Integer id;
    public String name;
    public String year;
    public MajorClassInfo(MajorClass c) { id = c.getId(); name = c.getName(); year = c.getYear(); }

    public static class WithMajor extends MajorClassInfo {
        public Major major;
        public WithMajor(MajorClass c) {
            super(c);
            this.major = c.getMajor();
        }
    }
}
