package com.javier.canvasbot.canvas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CanvasCourseSection(
        Long id,
        String name,
        @JsonProperty("start_at")
        String startAt,
        @JsonProperty("end_at")
        String endAt,
        @JsonProperty("enrollment_role")
        String enrollmentRole
) {}