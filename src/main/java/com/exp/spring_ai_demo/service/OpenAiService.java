package com.exp.spring_ai_demo.service;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import io.reactivex.Flowable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Service
public class OpenAiService {

    @Value("${spring.ai.dashscope.api-key}")
    private String dashscopeApiKey;

    private final ExecutorService executor = Executors.newCachedThreadPool();

    public void streamChat(String prompt, Consumer<String> onMessage, Runnable onComplete, Consumer<Throwable> onError) {
        executor.submit(() -> {
            try {
                Generation gen = new Generation();
                Message userMsg = Message.builder().role(Role.USER.getValue()).content(prompt).build();
                GenerationParam param = GenerationParam.builder()
                        .apiKey(dashscopeApiKey)
                        .model("qwen-plus")
                        .messages(Arrays.asList(userMsg))
                        .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                        .incrementalOutput(true)
                        .build();

                Flowable<GenerationResult> result = gen.streamCall(param);
                result.blockingForEach(message -> {
                    String content = message.getOutput().getChoices().get(0).getMessage().getContent();
                    if (content != null) {
                        onMessage.accept(content);
                    }
                });

                onComplete.run();
            } catch (ApiException | NoApiKeyException | InputRequiredException e) {
                onError.accept(e);
            }
        });
    }
}
