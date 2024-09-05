package backEnd.clush_backEnd.calendar.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CalendarEventDTO {
    private Long eventId;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean allDay;

    private boolean isShared;        // 공유된 일정 여부
    private String sharedByUser;     // 공유한 사용자
    private String sharedWithUser;   // 공유받은 사용자 (없으면 null)
}
