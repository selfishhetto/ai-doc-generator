package com.example.aidocgenerator.client.gemini.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiRequest {

    @JsonProperty("system_instruction")
    private GeminiContent systemInstruction;

    private List<GeminiContent> contents;
}
