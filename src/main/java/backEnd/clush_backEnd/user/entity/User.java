package backEnd.clush_backEnd.user.entity;

import backEnd.clush_backEnd.calendar.entity.CalendarEvent;
import backEnd.clush_backEnd.calendar.entity.EventShare;
import backEnd.clush_backEnd.todo.entity.ToDo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String email;

    private String password;

    // 관계 설정
    @OneToMany(mappedBy = "user")
    private List<ToDo> todos;

    @OneToMany(mappedBy = "user")
    private List<CalendarEvent> calendarEvents;

    // 내가 공유한 이벤트
    @OneToMany(mappedBy = "sharedByUser")
    private List<EventShare> sharedEventsByMe;

    // 나에게 공유된 이벤트
    @OneToMany(mappedBy = "sharedUser")
    private List<EventShare> sharedEventsWithMe;

    // 생성자와 필수 필드 세팅 메소드
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    // 연관관계 메서드
    public void addToDo(ToDo toDo) {
        this.todos.add(toDo);
        toDo.setUser(this);
    }

    public void removeToDo(ToDo toDo) {
        this.todos.remove(toDo);
        toDo.setUser(null);
    }

    public void addCalendarEvent(CalendarEvent event) {
        this.calendarEvents.add(event);
        event.assignUser(this);  // 명시적 메서드 호출
    }

    public void removeCalendarEvent(CalendarEvent event) {
        this.calendarEvents.remove(event);
        event.assignUser(null);  // 유저와의 관계 해제
    }

    public void addSharedEventByMe(EventShare eventShare) {
        this.sharedEventsByMe.add(eventShare);
        eventShare.setSharedByUser(this);
    }

    public void addSharedEventWithMe(EventShare eventShare) {
        this.sharedEventsWithMe.add(eventShare);
        eventShare.setSharedUser(this);
    }

    public void removeSharedEventByMe(EventShare eventShare) {
        this.sharedEventsByMe.remove(eventShare);
        eventShare.setSharedByUser(null);
    }

    public void removeSharedEventWithMe(EventShare eventShare) {
        this.sharedEventsWithMe.remove(eventShare);
        eventShare.setSharedUser(null);
    }

}
