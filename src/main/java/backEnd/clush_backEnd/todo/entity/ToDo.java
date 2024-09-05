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
    public void setStatus(ToDoStatus status) {
        this.status = status;
    }
}
