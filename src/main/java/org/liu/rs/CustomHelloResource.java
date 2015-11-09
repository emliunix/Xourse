package org.liu.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Liu Yuhui on 2015/10/31.
 */
public class CustomHelloResource {
    private String greeting;
    private UriInfo uriInfo;

    public CustomHelloResource(String greeting, UriInfo uriInfo) {
        this.greeting = greeting;
        this.uriInfo = uriInfo;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> get() {
        Map<String, Object> m = new HashMap<>();
        m.put("message", "Hello " + greeting + "!!!");
        List<Item> items = new ArrayList<>(2);
        items.add(new Item("who I love", "Sun Cai"));
        items.add(new Item("Test title", "happy everyday"));
        m.put("content", items);
        m.put("boolean", Boolean.TRUE);
        m.put("integer", 123);
        m.put("double", 123.4);
        Map<String, Object> m2 = new HashMap<>();
        m2.put("Path", uriInfo.getPath());
        m2.put("Absolute Path", uriInfo.getAbsolutePath());
        m2.put("Base Uri", uriInfo.getBaseUri());
        m2.put("Matched Uris", String.join(",", uriInfo.getMatchedURIs()));
        m2.put("Request Uri", uriInfo.getRequestUri());
        m.put("Additional Info", m2);
        return m;
    }

    public static class Item {
        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Item() {
        }

        public Item(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }
}
