package backEnd.clush_backEnd.calendar.repository;

import backEnd.clush_backEnd.calendar.entity.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {

    // 특정 사용자가 생성한 일정 조회
    List<CalendarEvent> findByUserId(Long userId);
}
