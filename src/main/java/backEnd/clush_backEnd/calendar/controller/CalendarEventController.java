package backEnd.clush_backEnd.calendar.controller;

import backEnd.clush_backEnd.calendar.DTO.CalendarEventDTO;
import backEnd.clush_backEnd.calendar.DTO.CreateOrUpdateEventRequestDTO;
import backEnd.clush_backEnd.calendar.DTO.DeleteEventRequestDTO;
import backEnd.clush_backEnd.calendar.DTO.ShareEventRequestDTO;
import backEnd.clush_backEnd.calendar.entity.CalendarEvent;
import backEnd.clush_backEnd.calendar.entity.EventShare;
import backEnd.clush_backEnd.calendar.service.CalendarEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class CalendarEventController {

    private final CalendarEventService calendarEventService;

    // 일정 생성
    @PostMapping("/create")
    public ResponseEntity<CalendarEvent> createEvent(
            @RequestParam Long userId,
            @RequestBody CreateOrUpdateEventRequestDTO requestDTO) {
        CalendarEvent event = calendarEventService.createEvent(userId, requestDTO);
        return ResponseEntity.ok(event);
    }

    // 일정 수정
    @PutMapping("/{eventId}/update")
    public ResponseEntity<CalendarEvent> updateEvent(
            @PathVariable Long eventId,
            @RequestBody CreateOrUpdateEventRequestDTO requestDTO) {
        CalendarEvent updatedEvent = calendarEventService.updateEvent(eventId, requestDTO);
        return ResponseEntity.ok(updatedEvent);
    }

    // 단일 일정 조회
    @GetMapping("/{eventId}")
    public ResponseEntity<CalendarEventDTO> getEventById(@PathVariable Long eventId) {
        Optional<CalendarEventDTO> eventDTO = calendarEventService.getEventById(eventId);
        return eventDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 유저가 생성한 일정과 공유받은 일정 모두 조회
    @GetMapping("/user/{userId}/events")
    public ResponseEntity<List<CalendarEventDTO>> getAllEventsForUser(@PathVariable Long userId) {
        List<CalendarEventDTO> events = calendarEventService.getAllEventsForUser(userId);
        return ResponseEntity.ok(events);
    }

    // 일정 삭제 (소유권 이전 여부 포함)
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteEvent(
            @RequestBody DeleteEventRequestDTO deleteEventRequestDTO,
            @RequestParam Long userId) {
        calendarEventService.deleteEvent(deleteEventRequestDTO, userId);
        return ResponseEntity.noContent().build();
    }
    // 일정 공유
    @PostMapping("/share")
    public ResponseEntity<EventShare> shareEvent(
            @RequestBody ShareEventRequestDTO shareEventRequestDTO) {
        EventShare eventShare = calendarEventService.shareEvent(shareEventRequestDTO);
        return ResponseEntity.ok(eventShare);
    }
    // 일정 확인 API
    @PostMapping("/event-share/{id}/viewed")
    public ResponseEntity<Void> markEventAsViewed(@PathVariable Long id) {
        calendarEventService.markEventAsViewed(id);
        return ResponseEntity.ok().build();
    }
    // 일정 공유 수락 API
    @PostMapping("/event-share/{id}/accept")
    public ResponseEntity<Void> acceptEventShare(@PathVariable Long id) {
        calendarEventService.acceptEventShare(id);
        return ResponseEntity.ok().build();
    }

    // 일정 공유 거절 API
    @PostMapping("/event-share/{id}/decline")
    public ResponseEntity<Void> declineEventShare(@PathVariable Long id) {
        calendarEventService.declineEventShare(id);
        return ResponseEntity.ok().build();
    }
}