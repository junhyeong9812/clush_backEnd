package backEnd.clush_backEnd.calendar.repository;

import backEnd.clush_backEnd.calendar.entity.EventShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventShareRepository extends JpaRepository<EventShare, Long> {
    List<EventShare> findBySharedUserId(Long sharedUserId);
    List<EventShare> findByEventId(Long eventId);
}
