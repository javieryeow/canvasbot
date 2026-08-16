package com.javier.canvasbot.canvas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CanvasPlannable(
        Long id,
        String title,
        @JsonProperty("due_at")
        String dueAt
) {}
