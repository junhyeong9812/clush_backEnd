package backEnd.clush_backEnd.calendar.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteEventRequestDTO {
    private Long eventId;                // 삭제할 일정 ID
    private boolean transferOwnership;   // 소유권 이전 여부
}
