package org.xourse.resource.info;

import org.xourse.entity.Major;

/**
 * Created by Liu Yuhui on 2015/12/2.
 */
public class MajorInfo {
    public Integer id;
    public String name;

    public MajorInfo(Major m) {
        this.id = m.getId();
        this.name = m.getName();
    }
}
