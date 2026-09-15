package com.example.aidocgenerator.prompt;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class PromptBuilder {

    private static final String SYSTEM_PROMPT_PATH = "prompts/system-prompt.txt";

    private final String systemPromptTemplate;

    public PromptBuilder() {
        this.systemPromptTemplate = loadSystemPrompt();
    }

    public String buildSystemPrompt() {
        return systemPromptTemplate;
    }

    public String buildUserPrompt(String code, String language, String style) {
        String lang = (language == null || language.isBlank()) ? "auto-detect" : language;
        String docStyle = (style == null || style.isBlank()) ? "default" : style;

        return """
                Language: %s
                Documentation style: %s

                Code:
                %s
                """.formatted(lang, docStyle, code);
    }

    private String loadSystemPrompt() {
        try (var inputStream = new ClassPathResource(SYSTEM_PROMPT_PATH).getInputStream()) {
            return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot load system prompt from " + SYSTEM_PROMPT_PATH, e);
        }
    }
}
