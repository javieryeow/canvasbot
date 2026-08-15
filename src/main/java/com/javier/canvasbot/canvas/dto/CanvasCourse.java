package com.javier.canvasbot.canvas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CanvasCourse(
        Long id,
        String name,
        @JsonProperty("course_code")
        String courseCode
) {}
