package com.example.aidocgenerator.client;

public interface AiClient {
    String complete(String systemPrompt, String userPrompt);
}
