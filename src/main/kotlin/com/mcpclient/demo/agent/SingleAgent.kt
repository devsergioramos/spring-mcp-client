package com.mcpclient.demo.agent

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.tool.ToolCallbackProvider
import org.springframework.stereotype.Component

@Component
class SingleAgent(
    chatClientBuilder: ChatClient.Builder,
    tools: ToolCallbackProvider
) {
    private val chatClient: ChatClient = chatClientBuilder.defaultToolCallbacks(tools).build()

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