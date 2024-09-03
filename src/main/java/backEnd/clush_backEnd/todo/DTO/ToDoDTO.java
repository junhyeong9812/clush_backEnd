package backEnd.clush_backEnd.todo.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ToDoDTO {
    private Long userId;
    private String title;
    private String description;

    public ToDoDTO(Long userId, String title, String description) {
        this.userId = userId;
        this.title = title;
        this.description = description;
    }
}
