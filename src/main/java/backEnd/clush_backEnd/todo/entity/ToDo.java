package backEnd.clush_backEnd.todo.entity;

import backEnd.clush_backEnd.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ToDo {
    @Id
    @GeneratedValue
    @Column(name = "todo_id")
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private ToDoStatus status = ToDoStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime writeDate;

    public ToDo( String title, String description, User user, LocalDateTime writeDate) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.writeDate = writeDate;
    }

    public void setUser(User user) {
        if (this.user != null) {
            this.user.getTodos().remove(this);
        }
        this.user = user;
        if (user != null && !user.getTodos().contains(this)) {
            user.getTodos().add(this);
        }
    }
    // 상태 변경 메서드 (상태에 따른 로직을 캡슐화)
    public void changeStatus(ToDoStatus newStatus) {
        switch (newStatus) {
            case PENDING:
                moveToPending();
                break;
            case IN_PROGRESS:
                startProgress();
                break;
            case COMPLETED:
                completeToDo();
                break;
            case CANCELLED:
                cancelToDo();
                break;
            default:
                throw new IllegalArgumentException("Invalid ToDoStatus");
        }
    }

    // 할 일 대기 중 상태로 변경
    private void moveToPending() {
        this.status = ToDoStatus.PENDING;
        // 추가 로직이 필요하면 여기에 작성
    }

    // 진행 중 상태로 변경
    private void startProgress() {
        this.status = ToDoStatus.IN_PROGRESS;
        // 추가 로직이 필요하면 여기에 작성
    }

    // 완료 상태로 변경
    private void completeToDo() {
        this.status = ToDoStatus.COMPLETED;
        // 완료 시 필요한 추가 로직
    }

    // 취소 상태로 변경
    private void cancelToDo() {
        this.status = ToDoStatus.CANCELLED;
        // 취소 시 필요한 추가 로직
    }
    //수정 메소드
    public void updateToDoDetails(String title, String description) {
        if (title != null && !title.isEmpty()) {
            this.title = title;
        }
        if (description != null && !description.isEmpty()) {
            this.description = description;
        }
    }
}
