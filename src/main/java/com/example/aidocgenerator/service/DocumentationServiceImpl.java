package com.example.aidocgenerator.service;

import com.example.aidocgenerator.dto.request.DocGenRequest;
import com.example.aidocgenerator.dto.response.DocGenResponse;
import org.springframework.stereotype.Service;

@Service
public class DocumentationServiceImpl implements DocumentationService {

    @Override
    public DocGenResponse generateDocumentation(DocGenRequest request) {
        // TODO: заменить на вызов PromptBuilder + AiClient
        return new DocGenResponse(request.getCode());
    }
}
