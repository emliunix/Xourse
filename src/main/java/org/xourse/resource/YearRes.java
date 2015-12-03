package org.xourse.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Year;

/**
 * Provide year data for autocompletion
 * Created by Liu Yuhui on 2015/12/3.
 */
@Path("/year")
public class YearRes {
    final YearData data = new YearData();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public YearData get() {
        return data;
    }

    public static class YearData {
        public String msg = "success";
        public boolean status = true;
        public String[] years = new String[] {
                "2001-2002",
                "2002-2003",
                "2003-2004",
                "2004-2005",
                "2005-2006",
                "2006-2007",
                "2007-2008",
                "2008-2009",
                "2009-2010",
                "2010-2011",
                "2011-2012",
                "2012-2013",
                "2013-2014",
                "2014-2015",
                "2015-2016",
                "2016-2017",
                "2017-2018",
                "2018-2019",
                "2019-2020"
        };
    }
}
