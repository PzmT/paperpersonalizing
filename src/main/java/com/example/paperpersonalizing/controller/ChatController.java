package com.example.paperpersonalizing.controller;

import com.gearwenxin.client.ernie.ErnieBotClient;
import com.gearwenxin.entity.response.ChatResponse;
import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import com.example.paperpersonalizing.API.BaseResponse;

import java.io.Serializable;

@Controller
@RequestMapping("/chat")
public class ChatController {

    // 要调用的模型的客户端
    @Resource
    private ErnieBotClient ernieBotClient;

    // 单次对话
    @PostMapping("/chat")
    public BaseResponse<String> chatSingle(String msg) {
        ChatResponse response = ernieBotClient.chatSingle(msg);
        return BaseResponse.success(response.getResult());
    }

    // 连续对话
    @PostMapping("/chats")
    public BaseResponse<String> chatCont(String msg) {
        String chatUID = "test-user-1001";
        ChatResponse response = ernieBotClient.chatCont(msg, chatUID);
        return BaseResponse.success(response.getResult());
    }

    // 流式返回,单次对话
    @PostMapping(value = "/stream/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponse> chatSingleStream(String msg) {
        Flux<ChatResponse> chatResponseFlux = ernieBotClient.chatSingleOfStream(msg);
        return chatResponseFlux;
    }

    // 流式返回,连续对话
    @PostMapping(value = "/stream/chats", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponse> chatContStream(String msg, String msgUid) {
        Flux<ChatResponse> chatResponseFlux = ernieBotClient.chatContOfStream(msg, msgUid);
        return chatResponseFlux;
    }

}