package org.xourse.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xourse.entity.News;
import org.xourse.service.NewsService;
import org.xourse.utils.MessageUtils;
import org.xourse.utils.TaskRunner;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Resource Template
 * Created by Liu Yuhui on 2015/11/26.
 */
@Component
@Path("/news")
public class NewsRes {

    @Autowired
    NewsService newsService;

    TaskRunner<Map<String, Object>> taskRunner;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> get() {
        return taskRunner.run(() -> {
            Map<String, Object> m = MessageUtils.success();
            List<News> ns = newsService.findAll();
            m.put("news", ns);
            return m;
        }, MessageUtils::fail);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> create(final News n) {
        return taskRunner.run(() -> {
            newsService.create(n);
            return MessageUtils.success();
        }, MessageUtils::fail);
    }

    @Path("{id : \\d+}")
    public NewsResource doPath(@PathParam("id")String id) {
        return new NewsResource(Integer.valueOf(id));
    }

    public class NewsResource {
        private int id;

        public NewsResource(int id) {
            this.id = id;
        }

//        @GET
//        @Produces(MediaType.APPLICATION_JSON)
//        public Map<String, Object> get() {
//            return null;
//        }

        @PUT
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> update(final News n) {
            return taskRunner.run(() -> {
                newsService.update(n);
                return MessageUtils.success();
            }, MessageUtils::fail);
        }

        @DELETE
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> delete() {
            return taskRunner.run(() -> {
                newsService.remove(new News(id));
                return MessageUtils.success();
            }, MessageUtils::fail);
        }
    }

}

