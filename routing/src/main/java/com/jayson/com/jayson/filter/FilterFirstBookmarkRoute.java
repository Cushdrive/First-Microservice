package com.jayson.com.jayson.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by jayson on 1/11/17.
 */
public class FilterFirstBookmarkRoute extends ZuulFilter{



    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        String newURL;
        RequestContext ctx = RequestContext.getCurrentContext();
        String oldURLs = ctx.getRequest().getRequestURL().toString();
        try {
            URL oldURL = new URL(oldURLs);
            String port = (oldURL.getPort() > 0 ? ":" + oldURL.getPort() : "");
            Map<String,List<String>> queryparams = ctx.getRequestQueryParams();
            if (!queryparams.isEmpty() && queryparams.containsKey("username")) {
                String un = queryparams.get("username").get(0).toString();
                newURL = oldURL.getProtocol() + "://" + oldURL.getHost() + port + "/" + un + "/bookmarks/1";

                ctx.setRouteHost(new URL(newURL));
            }
        }
        catch (MalformedURLException ex) {
            //Nom Nom Nom
        }
        return null;
    }
}
