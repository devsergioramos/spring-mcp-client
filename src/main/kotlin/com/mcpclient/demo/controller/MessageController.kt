package com.mcpclient.demo.controller

import com.mcpclient.demo.agent.SingleAgent
import com.mcpclient.demo.model.MessageRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/message/")
class MessageController(
    private val agent: SingleAgent
) {
    @PostMapping
    fun processMessage(
        @RequestBody message: MessageRequest
    ) = agent.process(message.inputText)
}