package com.yitianyigexiangfa.unsplash_mcp_server.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class UnsplashPhoto {
    private String id;
    private String description;
    private Map<String, String> urls;
    private Integer width;
    private Integer height;
}
