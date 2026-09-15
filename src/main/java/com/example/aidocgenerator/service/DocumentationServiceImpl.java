package com.example.aidocgenerator.service;

import com.example.aidocgenerator.client.AiClient;
import com.example.aidocgenerator.dto.request.DocGenRequest;
import com.example.aidocgenerator.dto.response.DocGenResponse;
import com.example.aidocgenerator.prompt.PromptBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentationServiceImpl implements DocumentationService {

    private final PromptBuilder promptBuilder;
    private final AiClient aiClient;

    @Override
    public DocGenResponse generateDocumentation(DocGenRequest request) {
        String systemPrompt = promptBuilder.buildSystemPrompt();
        String userPrompt = promptBuilder.buildUserPrompt(
                request.getCode(), request.getLanguage(), request.getStyle());

        String documentedCode = aiClient.complete(systemPrompt, userPrompt);
        return new DocGenResponse(documentedCode);
    }
}
