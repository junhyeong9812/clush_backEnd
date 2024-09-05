package backEnd.clush_backEnd.calendar.entity;

import backEnd.clush_backEnd.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CalendarEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id")
    private Long id;

    private String title;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean allDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;  // 일정을 소유한 사용자

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventShare> sharedUsers;



    // 생성자 (빌더와 함께 사용)
    @Builder
    public CalendarEvent(String title, String description, LocalDateTime startTime, LocalDateTime endTime, boolean allDay, User user) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.allDay = allDay;
        this.user = user;
    }

    // 유저 설정 메서드 (setUser 대신)
    public void assignUser(User user) {
        if (this.user != null) {
            this.user.getCalendarEvents().remove(this);
        }
        this.user = user;
        if (user != null && !user.getCalendarEvents().contains(this)) {
            user.getCalendarEvents().add(this);
        }
    }

    // 일정 수정 메서드 (빌더 패턴 사용)
    public CalendarEvent updateEvent(String title, String description, LocalDateTime startTime, LocalDateTime endTime, boolean allDay) {
        return CalendarEvent.builder()
                .title(title != null ? title : this.title)
                .description(description != null ? description : this.description)
                .startTime(startTime != null ? startTime : this.startTime)
                .endTime(endTime != null ? endTime : this.endTime)
                .allDay(allDay)
                .user(this.user)  // 기존 유저 유지
                .build();
    }
}