package backEnd.clush_backEnd.calendar.repository;

import backEnd.clush_backEnd.calendar.entity.EventShare;
import backEnd.clush_backEnd.calendar.entity.EventShareStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventShareRepository extends JpaRepository<EventShare, Long> {
    List<EventShare> findBySharedUserId(Long sharedUserId);
    List<EventShare> findByEventId(Long eventId);
    // 공유된 사용자 ID와 일정 상태로 일정 조회 (자동 생성됨)
    List<EventShare> findBySharedUserIdAndStatus(Long sharedUserId, EventShareStatus status);
}
