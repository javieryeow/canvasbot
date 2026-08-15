package com.javier.canvasbot;

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
    CommandLineRunner testCanvas(CanvasClient canvasClient) {
        return args -> {
            List<CanvasCourse> courses = canvasClient.getCourses();
            System.out.println("Total courses: " + courses.size());
            for (CanvasCourse course : courses) {
                System.out.println(
                        course.id()
                                + " | "
                                + course.courseCode()
                                + " | "
                                + course.name()
                );
            }
        };
    }

}
