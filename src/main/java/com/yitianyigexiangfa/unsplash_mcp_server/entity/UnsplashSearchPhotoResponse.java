package com.yitianyigexiangfa.unsplash_mcp_server.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UnsplashSearchPhotoResponse {

    private Integer total;
    private Integer totalPages;
    private List<UnsplashPhoto> results;

}
