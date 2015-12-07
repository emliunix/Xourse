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
                "2001-2002-1",
                "2001-2002-2",
                "2002-2003-1",
                "2002-2003-2",
                "2003-2004-1",
                "2003-2004-2",
                "2004-2005-1",
                "2004-2005-2",
                "2005-2006-1",
                "2005-2006-2",
                "2006-2007-1",
                "2006-2007-2",
                "2007-2008-1",
                "2007-2008-2",
                "2008-2009-1",
                "2008-2009-2",
                "2009-2010-1",
                "2009-2010-2",
                "2010-2011-1",
                "2010-2011-2",
                "2011-2012-1",
                "2011-2012-2",
                "2012-2013-1",
                "2012-2013-2",
                "2013-2014-1",
                "2013-2014-2",
                "2014-2015-1",
                "2014-2015-2",
                "2015-2016-1",
                "2015-2016-2",
                "2016-2017-1",
                "2016-2017-2",
                "2017-2018-1",
                "2017-2018-2",
                "2018-2019-1",
                "2018-2019-2",
                "2019-2020-1",
                "2019-2020-2"
        };
    }
}
