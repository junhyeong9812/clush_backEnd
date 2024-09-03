package backEnd.clush_backEnd.todo.entity;

import backEnd.clush_backEnd.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class ToDo {
    @Id
    @GeneratedValue
    @Column(name = "todo_id")
    private Long id;

    private String title;

    private String description;

    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime writeDate;

    public void setUser(User user) {
        if (this.user != null) {
            this.user.getTodos().remove(this);
        }
        this.user = user;
        if (user != null && !user.getTodos().contains(this)) {
            user.getTodos().add(this);
        }
    }
}
