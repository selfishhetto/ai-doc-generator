package com.example.aidocgenerator.client.gemini;

import com.example.aidocgenerator.client.AiClient;
import com.example.aidocgenerator.client.gemini.dto.GeminiContent;
import com.example.aidocgenerator.client.gemini.dto.GeminiPart;
import com.example.aidocgenerator.client.gemini.dto.GeminiRequest;
import com.example.aidocgenerator.client.gemini.dto.GeminiResponse;
import com.example.aidocgenerator.config.AiProperties;
import com.example.aidocgenerator.exception.AiServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GeminiClient implements AiClient {

    private final WebClient webClient;
    private final AiProperties aiProperties;

    @Override
    public String complete(String systemPrompt, String userPrompt) {
        GeminiRequest request = new GeminiRequest(
                new GeminiContent(List.of(new GeminiPart(systemPrompt))),
                List.of(new GeminiContent(List.of(new GeminiPart(userPrompt))))
        );

        String path = "/models/" + aiProperties.getModel() + ":generateContent";

        try {
            GeminiResponse response = webClient.post()
                    .uri(path)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(GeminiResponse.class)
                    .block();

            return extractText(response);
        } catch (Exception e) {
            throw new AiServiceException("Gemini API call failed: " + e.getMessage(), e);
        }
    }

    private String extractText(GeminiResponse response) {
        if (response == null || response.getCandidates() == null || response.getCandidates().isEmpty()) {
            throw new AiServiceException("Gemini returned no candidates");
        }

        GeminiContent content = response.getCandidates().get(0).getContent();
        if (content == null || content.getParts() == null || content.getParts().isEmpty()) {
            throw new AiServiceException("Gemini response has no content parts");
        }

        return content.getParts().get(0).getText();
    }
}
