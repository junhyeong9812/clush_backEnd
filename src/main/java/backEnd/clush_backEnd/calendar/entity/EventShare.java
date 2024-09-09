package backEnd.clush_backEnd.calendar.entity;

import backEnd.clush_backEnd.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    // 일정 확인 여부 (기본값: false)
    private Boolean isViewed = false;

    // 공유 상태 (PENDING, ACCEPTED, DECLINED)
    @Enumerated(EnumType.STRING)
//    private EventShareStatus status = EventShareStatus.PENDING;
    private EventShareStatus status = EventShareStatus.ACCEPTED;

    // 일정 확인 상태 업데이트
    public void markAsViewed() {
        this.isViewed = true;
    }


    // 생성자 추가
    public EventShare(CalendarEvent event, User sharedByUser, User sharedUser, LocalDateTime sharedAt) {
        setEvent(event);
        setSharedByUser(sharedByUser);
        setSharedUser(sharedUser);
        this.sharedAt = sharedAt;
    }


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

    // 일정 수락
    public void accept() {
        this.status = EventShareStatus.ACCEPTED;
    }

    // 일정 거절
    public void decline() {
        this.status = EventShareStatus.DECLINED;
    }
}
