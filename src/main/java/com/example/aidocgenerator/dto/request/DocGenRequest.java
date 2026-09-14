package com.example.aidocgenerator.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocGenRequest {

    @NotBlank(message = "code must not be blank")
    @Size(max = 20_000, message = "code is too long (max 20000 chars)")
    private String code;

    private String language;
    private String style;
}
