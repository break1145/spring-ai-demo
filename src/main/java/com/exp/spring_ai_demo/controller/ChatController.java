package com.exp.spring_ai_demo.controller;


import com.exp.spring_ai_demo.entiry.PromptRequest;
import com.exp.spring_ai_demo.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private OpenAiService openAiService;

    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(@RequestBody PromptRequest request) {
        SseEmitter emitter = new SseEmitter(0L); // 无限超时

        openAiService.streamChat(
                request.getPrompt(),
                content -> {
                    try {
                        emitter.send(SseEmitter.event().data(content));
                    } catch (Exception e) {
                        emitter.completeWithError(e);
                    }
                },
                emitter::complete,
                emitter::completeWithError
        );

        return emitter;
    }

}
