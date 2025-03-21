package com.yitianyigexiangfa.unsplash_mcp_server;

import com.yitianyigexiangfa.unsplash_mcp_server.tools.UnsplashTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UnsplashMcpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnsplashMcpServerApplication.class, args);
	}

	@Bean
	public ToolCallbackProvider tools(UnsplashTool tool) {
		return MethodToolCallbackProvider.builder()
				.toolObjects(tool)
				.build();
	}

}
