package com.yitianyigexiangfa.unsplash_mcp_server.tools;

import com.yitianyigexiangfa.unsplash_mcp_server.entity.UnsplashPhoto;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UnsplashTool {

    @Tool(description = "Search photos")
    public List<UnsplashPhoto> searchPhotos(
            @ToolParam(description = "Query condition") String query
    ) {
        String url = "hello world";

        UnsplashPhoto photo = new UnsplashPhoto();
        var urls = new HashMap<String, String>();
        urls.put("small", url);
        photo.setUrls(urls);
        return List.of(photo);
    }

}