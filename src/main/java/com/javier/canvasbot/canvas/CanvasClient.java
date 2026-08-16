package com.javier.canvasbot.canvas;

import com.javier.canvasbot.canvas.dto.CanvasPlannerItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.javier.canvasbot.canvas.dto.CanvasCourse;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CanvasClient {
    private final RestClient restClient;

    public CanvasClient(
            @Value("${canvas.base-url}") String baseUrl,
            @Value("${canvas.token}") String token
    ) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + token)
                .build();
    }

    public List<CanvasCourse> getCourses() {
        List<CanvasCourse> allCourses = new ArrayList<>();
        URI uri = UriComponentsBuilder
                .fromPath("/api/v1/courses")
                .queryParam("enrollment_state", "active")
                .queryParam("include[]", "sections")
                .queryParam("include[]", "term")
                .queryParam("per_page", 100)
                .build()
                .toUri();
        while (uri != null) {
            ResponseEntity<CanvasCourse[]> response = restClient.get()
                    .uri(uri)
                    .retrieve()
                    .toEntity(CanvasCourse[].class);
            CanvasCourse[] courses = response.getBody();
            if (courses != null) {
                allCourses.addAll(Arrays.asList(courses));
            }
            uri = getNextPageUri(response.getHeaders().getFirst("Link"));
        }
        return allCourses;
    }

    public List<CanvasPlannerItem> getPlannerItems(String startDate, String endDate) {
        URI uri = UriComponentsBuilder
                .fromPath("/api/v1/planner/items")
                .queryParam("start_date", startDate)
                .queryParam("end_date", endDate)
                .queryParam("filter", "incomplete_items")
                .build()
                .toUri();
        CanvasPlannerItem[] items = restClient.get()
                .uri(uri)
                .retrieve()
                .body(CanvasPlannerItem[].class);
        if (items == null) {
            return List.of();
        }
        return Arrays.asList(items);
    }

    private URI getNextPageUri(String linkHeader) {
        if (linkHeader == null) {
            return null;
        }
        for (String link : linkHeader.split(",")) {
            if (link.contains("rel=\"next\"")) {
                int start = link.indexOf("<") + 1;
                int end = link.indexOf(">");
                return URI.create(
                        link.substring(start, end)
                );
            }
        }
        return null;
    }
}
