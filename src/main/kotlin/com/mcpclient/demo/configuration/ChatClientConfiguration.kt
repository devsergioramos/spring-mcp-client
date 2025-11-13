package com.mcpclient.demo.configuration

import org.springframework.ai.chat.client.ChatClient
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatClientConfiguration {
    @Bean
    @Qualifier("defaultChatClient")
    fun defaultChatClient(builder: ChatClient.Builder) = builder.build()
}