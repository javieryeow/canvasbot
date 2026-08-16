package com.javier.canvasbot;

import com.javier.canvasbot.canvas.dto.CanvasCourseSection;
import com.javier.canvasbot.canvas.dto.CanvasPlannerItem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.javier.canvasbot.telegram.TelegramService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.javier.canvasbot.canvas.CanvasClient;
import com.javier.canvasbot.canvas.dto.CanvasCourse;
import tools.jackson.databind.JsonNode;

import java.util.List;

@SpringBootApplication
public class CanvasbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanvasbotApplication.class, args);
    }

    @Bean
    CommandLineRunner testCanvas(
            CanvasClient canvasClient,
            TelegramService telegramService
    ) {
        return args -> {
            List<CanvasCourse> courses = canvasClient.getCourses();

            StringBuilder message = new StringBuilder();

            for (CanvasCourse course : courses) {
                message.append("Course:\n")
                        .append(course)
                        .append("\n");

                message.append("\n");
            }

            List<CanvasPlannerItem> items = canvasClient.getPlannerItems(
                    "2026-08-16",
                    "2026-12-21"
            );

            message.append("Planner items: ")
                    .append(items.size())
                    .append("\n\n");

            for (CanvasPlannerItem item : items) {
                message.append(item)
                        .append("\n\n");
            }

            telegramService.sendMessage(message.toString());
        };
    }
}
