package backEnd.clush_backEnd.todo.DTO;

import backEnd.clush_backEnd.todo.entity.ToDoStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SelectToDoDTO {
    private Long id;                // ToDo ID
    private String title;           // 제목
    private String description;     // 설명
    private ToDoStatus status;      // 현재 할 일의 상태 (Enum)
    private Long userId;            // 작성자 ID
    private String username;        // 작성자 이름
    private LocalDateTime writeDate; // 작성일
    private List<ToDoStatus> statusOptions;  // 선택 가능한 상태 목록

    // 생성자에 상태 목록 추가
    public SelectToDoDTO(Long id, String title, String description, ToDoStatus status, Long userId, String username, LocalDateTime writeDate, List<ToDoStatus> statusOptions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.userId = userId;
        this.username = username;
        this.writeDate = writeDate;
        this.statusOptions = statusOptions;
    }
}
