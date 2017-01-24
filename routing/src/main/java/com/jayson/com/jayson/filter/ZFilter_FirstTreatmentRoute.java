package com.jayson.com.jayson.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.util.HTTPRequestUtils;
import org.springframework.cloud.netflix.ribbon.support.RibbonRequestCustomizer;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandContext;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandFactory;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonRoutingFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by jayson on 1/11/17.
 */
public class ZFilter_FirstTreatmentRoute extends RibbonRoutingFilter{

    private boolean useServletThirtyOne = true;

    public boolean isUseServletThirtyOne() {
        return useServletThirtyOne;
    }

    public void setUseServletThirtyOne(boolean useServletThirtyOne) {
        this.useServletThirtyOne = useServletThirtyOne;
    }

    public ZFilter_FirstTreatmentRoute(ProxyRequestHelper helper, RibbonCommandFactory<?> ribbonCommandFactory, List<RibbonRequestCustomizer> requestCustomizers) {
        super(helper, ribbonCommandFactory, requestCustomizers);

        try {
            HttpServletResponse.class.getMethod("getContentLengthLong");
        } catch(NoSuchMethodException e) {
            useServletThirtyOne = false;
        }
    }

    public ZFilter_FirstTreatmentRoute(RibbonCommandFactory<?> ribbonCommandFactory) {
        super(ribbonCommandFactory);
    }

    @Override
    public String filterType() {
        return "route";
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
    protected RibbonCommandContext buildCommandContext(RequestContext context) {
        HttpServletRequest request = context.getRequest();

        MultiValueMap<String, String> headers = this.helper
                .buildZuulRequestHeaders(request);
        MultiValueMap<String, String> params = this.helper
                .buildZuulRequestQueryParams(request);
        String verb = getVerb(request);
        InputStream requestEntity = getRequestBody(request);
        if (request.getContentLength() < 0) {
            context.setChunkedRequestBody();
        }

        String serviceId = (String) context.get("serviceId");
        Boolean retryable = (Boolean) context.get("retryable");

        String newURI = this.helper.buildZuulRequestURI(request);
        String oldUris = newURI;
        try {
            URI oldUri = new URI(oldUris);
            String port = (oldUri.getPort() > 0 ? ":" + oldUri.getPort() : "");
            Map<String, List<String>> queryparams = context.getRequestQueryParams();
            if (!CollectionUtils.isEmpty(queryparams) && queryparams.containsKey("username")) {
                String un = queryparams.get("username").get(0).toString();
                //newURI = oldUri.getProtocol() + "://" + oldURL.getHost() + port + "/api/" + un + "/bookmarks/1";
                newURI = "/api/jbender/treatments/1";
            }
        }
        catch (URISyntaxException ex) {
            //Nom Nom Nom
        }

        // remove double slashes
        newURI = newURI.replace("//", "/");

        long contentLength = useServletThirtyOne ? request.getContentLengthLong(): request.getContentLength();

        return new RibbonCommandContext(serviceId, verb, newURI, retryable, headers, params,
                requestEntity, this.requestCustomizers, contentLength);
    }

    /*
    @Override
    public String filterType() {
        return "route";
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
            if (!CollectionUtils.isEmpty(queryparams) && queryparams.containsKey("username")) {
                String un = queryparams.get("username").get(0).toString();
                //newURL = oldURL.getProtocol() + "://" + oldURL.getHost() + port + "/api/" + un + "/bookmarks/1";
                newURL = "http://127.0.0.1:57697/api/jbender/bookmarks/1";
                ctx.setRouteHost(new URL(newURL));
            }
            newURL = "http://localhost:57697";
            ctx.setRouteHost(new URL(newURL));
        }
        catch (Exception ex) {
            //Nom Nom Nom
            String test = "blah";
        }
        return null;
    }
    */
}