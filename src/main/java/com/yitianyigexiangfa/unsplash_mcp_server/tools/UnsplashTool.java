package com.yitianyigexiangfa.unsplash_mcp_server.tools;

import com.alibaba.fastjson.JSON;
import com.yitianyigexiangfa.unsplash_mcp_server.entity.UnsplashSearchPhotoResponse;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UnsplashTool {

    private final HttpClient httpClient;

    public UnsplashTool(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Tool(description = "Search photos")
    public  UnsplashSearchPhotoResponse searchPhotos(
            @ToolParam(description = "Query condition") String query,
            @ToolParam(description = "Page number", required = false) Integer page,
            @ToolParam(description = "Page size", required = false) Integer perPage,
            @ToolParam(description = "Order by", required = false) String orderBy,
            @ToolParam(description = "Color", required = false) String color,
            @ToolParam(description = "Orientation", required = false) String orientation
    ) {
        var params = new HashMap<String, String>();
        params.put("query", query);
        if (page != null) {
            params.put("page", String.valueOf(page));
        }
        if (perPage != null) {
            params.put("per_page", String.valueOf(perPage));
        }else{
            params.put("per_page", "1");
        }
        params.put("order_by", Objects.requireNonNullElse(orderBy, "relevant"));
        if (color != null) {
            params.put("color", color);
        }
        if (orientation != null) {
            params.put("orientation", orientation);
        }
        return searchPhoto(params);
    }

    public UnsplashSearchPhotoResponse searchPhoto(Map<String, String> params) {

        String query = buildQueryString(params);
        String unsplashAccessKey = System.getenv("UNSPLASH_ACCESS_KEY");
        if (unsplashAccessKey == null) {
            throw new RuntimeException("UNSPLASH_ACCESS_KEY is not set");
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.unsplash.com/search/photos" + "?" + query))
                .header("Accept-Version", "v1")
                .header("Authorization", "Client-ID " + unsplashAccessKey)
                .GET()
                .build();

        UnsplashSearchPhotoResponse unsplashSearchPhotoResponse = null;
        try {
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode () == 200) {
                String responseBody = response.body ();
                unsplashSearchPhotoResponse = JSON.parseObject(responseBody, UnsplashSearchPhotoResponse.class);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return unsplashSearchPhotoResponse;
    }

    private static String buildQueryString(Map<String, String> params) {
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!query.isEmpty()) {
                query.append("&");
            }
            query.append(encode(entry.getKey()))
                    .append("=")
                    .append(encode(entry.getValue()));
        }
        return query.toString();
    }

    private static String encode(String value) {
        return java.net.URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

}