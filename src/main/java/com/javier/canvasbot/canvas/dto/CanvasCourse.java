package com.javier.canvasbot.canvas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import tools.jackson.databind.JsonNode;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CanvasCourse(
        Long id,
        String name,
        @JsonProperty("course_code")
        String courseCode,
        List<CanvasCourseSection> sections,
        @JsonProperty("access_restricted_by_date")
        Boolean accessRestrictedByDate,
        @JsonProperty("enrollment_term_id")
        Long enrollmentTermId,
        @JsonProperty("created_at")
        String createdAt,
        @JsonProperty("start_at")
        String startAt,
        @JsonProperty("end_at")
        String endAt,
        JsonNode term
) {}
