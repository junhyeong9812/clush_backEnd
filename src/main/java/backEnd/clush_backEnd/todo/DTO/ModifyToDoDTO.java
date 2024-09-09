package backEnd.clush_backEnd.todo.DTO;

import backEnd.clush_backEnd.todo.entity.ToDoStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ModifyToDoDTO {
    private Long userId;
    private String title;
    private String description;
    private ToDoStatus status;

    public ModifyToDoDTO(Long userId, String title, String description, ToDoStatus status) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.status = status;
    }
}
