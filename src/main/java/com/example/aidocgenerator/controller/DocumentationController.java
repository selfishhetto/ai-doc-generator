package com.example.aidocgenerator.controller;

import com.example.aidocgenerator.dto.request.DocGenRequest;
import com.example.aidocgenerator.dto.response.DocGenResponse;
import com.example.aidocgenerator.service.DocumentationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/docs")
@RequiredArgsConstructor
public class DocumentationController {

    private final DocumentationService documentationService;

    @PostMapping("/generate")
    public ResponseEntity<DocGenResponse> generate(@Valid @RequestBody DocGenRequest request) {
        return ResponseEntity.ok(documentationService.generateDocumentation(request));
    }
}
