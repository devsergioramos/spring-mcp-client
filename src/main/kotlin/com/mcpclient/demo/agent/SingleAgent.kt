package com.mcpclient.demo.agent

import io.modelcontextprotocol.client.McpSyncClient
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider
import org.springframework.stereotype.Component

@Component
class SingleAgent(
    chatClientBuilder: ChatClient.Builder,
    mcpSyncClient: List<McpSyncClient>
) {
    private val mcpToolProvider = SyncMcpToolCallbackProvider(mcpSyncClient)

    private val chatClient: ChatClient = chatClientBuilder.defaultToolCallbacks(mcpToolProvider).build()

    fun process(
        input: String
    ): String? = runCatching {
        chatClient.prompt(input)
            .call()
            .content()
    }.onFailure {
        println("Erro ao processar mensagem: ${it.message}")
    }.getOrNull()
}