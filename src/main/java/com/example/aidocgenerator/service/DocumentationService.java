package com.example.aidocgenerator.service;

import com.example.aidocgenerator.dto.request.DocGenRequest;
import com.example.aidocgenerator.dto.response.DocGenResponse;

public interface DocumentationService {
    DocGenResponse generateDocumentation(DocGenRequest request);
}
