package com.javier.canvasbot.canvas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CanvasPlannerItem (
        @JsonProperty("plannable_id")
        Long plannableId,
        @JsonProperty("course_id")
        Long courseId,
        @JsonProperty("plannable_type")
        String plannableType,
        @JsonProperty("plannable_date")
        String plannableDate,
        Object submissions,
        CanvasPlannable plannable
) {}