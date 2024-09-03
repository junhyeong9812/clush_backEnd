package backEnd.clush_backEnd.calendar.entity;

import backEnd.clush_backEnd.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class CalendarEvent {
    @Id
    @GeneratedValue
    @Column(name = "calendar_id")
    private Long id;

    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // 일정을 소유한 사용자

    @OneToMany(mappedBy = "event")
    private List<EventShare> sharedUsers;

    public void setUser(User user) {
        if (this.user != null) {
            this.user.getCalendarEvents().remove(this);
        }
        this.user = user;
        if (user != null && !user.getCalendarEvents().contains(this)) {
            user.getCalendarEvents().add(this);
        }
    }
}
