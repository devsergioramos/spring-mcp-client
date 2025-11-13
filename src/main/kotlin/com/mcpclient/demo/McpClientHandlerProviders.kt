package com.mcpclient.demo

import io.modelcontextprotocol.spec.McpSchema
import io.modelcontextprotocol.spec.McpSchema.ElicitResult
import io.modelcontextprotocol.spec.McpSchema.Role
import org.springaicommunity.mcp.annotation.McpElicitation
import org.springaicommunity.mcp.annotation.McpSampling
import org.springframework.ai.chat.client.ChatClient
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service


@Service
class McpClientHandlerProviders(
    private val applicationContext: ApplicationContext,
) {
    @McpElicitation(clients = ["mcp-server"])
    fun elicitationHandler(request: McpSchema.ElicitRequest): ElicitResult {
        if (request.message.startsWith("Qual item você deseja alterar o preço?")) {
            val changedItem = mapOf(
                "itemId" to 1,
                "itemPrice" to "59.99"
            )

            return ElicitResult(ElicitResult.Action.ACCEPT, changedItem)
        }

        if (request.message.equals("Deseja confirmar alteração?")) {
            val confirmChange = mapOf(
                "confirm" to true
            )

            return ElicitResult(ElicitResult.Action.ACCEPT, confirmChange)
        }

        return ElicitResult(ElicitResult.Action.DECLINE, null)
    }

    @McpSampling(clients = ["mcp-server"])
    fun samplingHandler(llmRequest: McpSchema.CreateMessageRequest): McpSchema.CreateMessageResult {
        val chatClient = applicationContext.getBean("defaultChatClient", ChatClient::class.java)

        val userFilter = llmRequest.messages.first().content as McpSchema.TextContent

        val response = chatClient.prompt()
            .user(userFilter.text)
            .system(llmRequest.systemPrompt)
            .call()
            .content()

        return McpSchema.CreateMessageResult.builder()
            .role(Role.ASSISTANT)
            .content(McpSchema.TextContent(response))
            .build()
    }
}