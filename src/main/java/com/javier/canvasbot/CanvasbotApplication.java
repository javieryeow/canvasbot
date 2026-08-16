package com.javier.canvasbot;

import com.javier.canvasbot.canvas.dto.CanvasPlannerItem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.javier.canvasbot.telegram.TelegramService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.javier.canvasbot.canvas.CanvasClient;
import com.javier.canvasbot.canvas.dto.CanvasCourse;
import java.util.List;

@SpringBootApplication
public class CanvasbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanvasbotApplication.class, args);
    }

    @Bean
    CommandLineRunner testPlanner(CanvasClient canvasClient) {
        return args -> {
            List<CanvasPlannerItem> items = canvasClient.getPlannerItems(
                    "2026-08-16",
                    "2026-10-21"
            );

            System.out.println("Planner items: " + items.size());

            for (CanvasPlannerItem item : items) {
                System.out.println(item);
            }
        };
    }
}
