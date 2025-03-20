package com.yitianyigexiangfa;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.StdioServerTransport;
import io.modelcontextprotocol.spec.McpSchema;

import java.util.ArrayList;
import java.util.List;

public class UnsplashMcpServer {
    public static void main(String[] args) {
        StdioServerTransport transport = new StdioServerTransport(new ObjectMapper());

        var capabilities = McpSchema.ServerCapabilities.builder()
                .tools(true)
                .logging()
                .build();

        McpSyncServer syncServer = McpServer.sync(transport)
                .serverInfo("Unsplash", "1.0.0")
                .capabilities(capabilities)
                .build();

        var syncToolRegistration = getSyncToolRegistration();


        syncServer.addTool(syncToolRegistration);

        syncServer.loggingNotification(McpSchema.LoggingMessageNotification.builder()
                .level(McpSchema.LoggingLevel.INFO)
                .logger("custom-logger")
                .data("Server initialized")
                .build());
    }

    private static McpServerFeatures.SyncToolRegistration getSyncToolRegistration() {
        var schema = """
                {
                  "type" : "object",
                  "id" : "urn:jsonschema:Operation",
                  "properties" : {
                    "operation" : {
                      "type" : "string"
                    },
                    "a" : {
                      "type" : "number"
                    },
                    "b" : {
                      "type" : "number"
                    }
                  }
                }
                """;
        var syncToolRegistration = new McpServerFeatures.SyncToolRegistration(
                new McpSchema.Tool("calculator", "Basic calculator", schema),
                arguments -> {
                    List<McpSchema.Content> result = new ArrayList<>();
                    var textContent = new McpSchema.TextContent("Hello world");
                    result.add(textContent);
                    // Tool implementation
                    return new McpSchema.CallToolResult(result, false);
                }
        );
        return syncToolRegistration;
    }
}
