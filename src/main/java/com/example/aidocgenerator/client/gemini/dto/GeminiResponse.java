package com.example.aidocgenerator.client.gemini.dto;

import lombok.Data;

import java.util.List;

@Data
public class GeminiResponse {
    private List<GeminiCandidate> candidates;
}
