package com.javier.canvasbot.canvas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.javier.canvasbot.canvas.dto.CanvasCourse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;

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
        String url = "/api/v1/courses"
                + "?enrollment_state=active"
                + "&per_page=100";
        while (url != null) {
            ResponseEntity<CanvasCourse[]> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .toEntity(CanvasCourse[].class);
            CanvasCourse[] courses = response.getBody();
            if (courses != null) {
                allCourses.addAll(Arrays.asList(courses));
            }
            url = getNextPageUrl(response.getHeaders().getFirst("Link"));
        }
        return allCourses;
    }

    private String getNextPageUrl(String linkHeader) {
        if (linkHeader == null) {
            return null;
        }
        for (String link : linkHeader.split(",")) {
            if (link.contains("rel=\"next\"")) {
                int start = link.indexOf("<") + 1;
                int end = link.indexOf(">");
                return link.substring(start, end);
            }
        }
        return null;
    }
}
