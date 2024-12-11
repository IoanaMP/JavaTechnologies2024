/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.Filters;

import javax.ws.rs.container.*;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.ws.rs.HttpMethod;

/**
 *
 * @author ioana
 */
@Provider
public class EvaluationCache implements ContainerRequestFilter, ContainerResponseFilter {

    private final Map<String, String> cache = new ConcurrentHashMap<>();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String method = requestContext.getMethod();

        if (HttpMethod.GET.equalsIgnoreCase(method)) {
            String cacheKey = buildCacheKey(requestContext);
            if (cache.containsKey(cacheKey)) {
                String cachedResponse = cache.get(cacheKey);

                requestContext.abortWith(javax.ws.rs.core.Response.ok(cachedResponse).build());
                System.out.println("Served from cache: " + cacheKey);
            }
        } else if (HttpMethod.POST.equalsIgnoreCase(method) ||
                   HttpMethod.PUT.equalsIgnoreCase(method) ||
                   HttpMethod.DELETE.equalsIgnoreCase(method)) {
            cache.clear();
            System.out.println("Cache cleared due to modification: " + method);
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (HttpMethod.GET.equalsIgnoreCase(requestContext.getMethod())) {
            String cacheKey = buildCacheKey(requestContext);

            if (responseContext.hasEntity()) {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                OutputStream originalStream = responseContext.getEntityStream();

                responseContext.setEntityStream(new OutputStream() {
                    @Override
                    public void write(int b) throws IOException { //for one byte
                        buffer.write(b);
                        originalStream.write(b);
                    }

                    @Override
                    public void write(byte[] b) throws IOException { //for an entire byte array
                        buffer.write(b);
                        originalStream.write(b);
                    }

                    @Override
                    public void write(byte[] b, int off, int len) throws IOException { //for a subset of a byte array
                        buffer.write(b, off, len);
                        originalStream.write(b, off, len);
                    }
                });


                String responseBody = buffer.toString();
                cache.put(cacheKey, responseBody);
                System.out.println("Response cached for key: " + cacheKey);
            }
        }
    }

    private String buildCacheKey(ContainerRequestContext requestContext) {
        StringBuilder key = new StringBuilder(requestContext.getUriInfo().getPath());
        MultivaluedMap<String, String> queryParams = requestContext.getUriInfo().getQueryParameters();
        queryParams.forEach((k, v) -> key.append(";").append(k).append("=").append(String.join(",", v)));
        return key.toString();
    }
}