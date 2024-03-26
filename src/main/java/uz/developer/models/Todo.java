package uz.developer.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class Todo {
    private Integer id;
    private String title;
    private String priority;
    private String createdAt;

    @Builder
    public Todo(Integer id, String title, String priority) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}
