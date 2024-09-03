package backEnd.clush_backEnd.calendar.entity;

import backEnd.clush_backEnd.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class EventShare {
    @Id
    @GeneratedValue
    @Column(name = "event_share_id")
    private Long id;

    // 공유된 일정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private CalendarEvent event;

    // 일정 공유를 수행한 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_by_user_id")
    private User sharedByUser;

    // 일정을 공유받은 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_user_id")
    private User sharedUser;

    // 공유된 시간
    private LocalDateTime sharedAt;
    public void setEvent(CalendarEvent event) {
        if (this.event != null) {
            this.event.getSharedUsers().remove(this);
        }
        this.event = event;
        if (event != null && !event.getSharedUsers().contains(this)) {
            event.getSharedUsers().add(this);
        }
    }

    public void setSharedByUser(User user) {
        if (this.sharedByUser != null) {
            this.sharedByUser.getSharedEventsByMe().remove(this);
        }
        this.sharedByUser = user;
        if (user != null && !user.getSharedEventsByMe().contains(this)) {
            user.getSharedEventsByMe().add(this);
        }
    }

    public void setSharedUser(User user) {
        if (this.sharedUser != null) {
            this.sharedUser.getSharedEventsWithMe().remove(this);
        }
        this.sharedUser = user;
        if (user != null && !user.getSharedEventsWithMe().contains(this)) {
            user.getSharedEventsWithMe().add(this);
        }
    }
}
