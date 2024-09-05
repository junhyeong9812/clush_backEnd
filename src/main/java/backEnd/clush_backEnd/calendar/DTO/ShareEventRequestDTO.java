package backEnd.clush_backEnd.calendar.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareEventRequestDTO {
    private Long eventId;           // 공유할 일정 ID
    private Long sharedByUserId;    // 공유자 ID
    private Long sharedUserId;      // 공유받을 사용자 ID
}
