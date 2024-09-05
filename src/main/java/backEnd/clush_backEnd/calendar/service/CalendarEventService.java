package backEnd.clush_backEnd.calendar.service;

import backEnd.clush_backEnd.calendar.DTO.CalendarEventDTO;
import backEnd.clush_backEnd.calendar.DTO.CreateOrUpdateEventRequestDTO;
import backEnd.clush_backEnd.calendar.DTO.DeleteEventRequestDTO;
import backEnd.clush_backEnd.calendar.DTO.ShareEventRequestDTO;
import backEnd.clush_backEnd.calendar.entity.CalendarEvent;
import backEnd.clush_backEnd.calendar.entity.EventShare;
import backEnd.clush_backEnd.calendar.repository.CalendarEventRepository;
import backEnd.clush_backEnd.calendar.repository.EventShareRepository;
import backEnd.clush_backEnd.user.entity.User;
import backEnd.clush_backEnd.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional  // 전체 서비스에 트랜잭션 적용
public class CalendarEventService {

    private final CalendarEventRepository calendarEventRepository;
    private final UserRepository userRepository;
    private final EventShareRepository eventShareRepository;

    // 일정 생성
    public CalendarEvent createEvent(Long userId, CreateOrUpdateEventRequestDTO requestDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        CalendarEvent event = CalendarEvent.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .startTime(requestDTO.getStartTime())
                .endTime(requestDTO.getEndTime())
                .allDay(requestDTO.isAllDay())
                .user(user)
                .build();
        return calendarEventRepository.save(event);
    }

    // 일정 수정
    public CalendarEvent updateEvent(Long eventId, CreateOrUpdateEventRequestDTO requestDTO) {
        CalendarEvent event = calendarEventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        event = CalendarEvent.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .startTime(requestDTO.getStartTime())
                .endTime(requestDTO.getEndTime())
                .allDay(requestDTO.isAllDay())
                .user(event.getUser())  // 기존 소유자 유지
                .build();
        return calendarEventRepository.save(event);
    }

    // 단일 일정 조회 (읽기 전용)
    @Transactional(readOnly = true)
    public Optional<CalendarEventDTO> getEventById(Long eventId) {
        return calendarEventRepository.findById(eventId)
                .map(event -> new CalendarEventDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getStartTime(),
                        event.getEndTime(),
                        event.isAllDay(),
                        false,
                        null,
                        null
                ));
    }

    // 유저 일정 및 공유받은 일정 조회 (읽기 전용)
    @Transactional(readOnly = true)
    public List<CalendarEventDTO> getAllEventsForUser(Long userId) {
        List<CalendarEvent> userEvents = calendarEventRepository.findByUserId(userId);
        List<EventShare> sharedEvents = eventShareRepository.findBySharedUserId(userId);

        List<CalendarEventDTO> eventDTOs = userEvents.stream()
                .map(event -> new CalendarEventDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getStartTime(),
                        event.getEndTime(),
                        event.isAllDay(),
                        false,
                        null,
                        null
                ))
                .collect(Collectors.toList());

        List<CalendarEventDTO> sharedEventDTOs = sharedEvents.stream()
                .map(share -> new CalendarEventDTO(
                        share.getEvent().getId(),
                        share.getEvent().getTitle(),
                        share.getEvent().getDescription(),
                        share.getEvent().getStartTime(),
                        share.getEvent().getEndTime(),
                        share.getEvent().isAllDay(),
                        true,
                        share.getSharedByUser().getUsername(),
                        share.getSharedUser().getUsername()
                ))
                .collect(Collectors.toList());

        eventDTOs.addAll(sharedEventDTOs);
        return eventDTOs;
    }

    // 일정 삭제 로직
    public void deleteEvent(DeleteEventRequestDTO requestDTO, Long userId) {
        Long eventId = requestDTO.getEventId();
        boolean transferOwnership = requestDTO.isTransferOwnership();

        CalendarEvent event = calendarEventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        List<EventShare> sharedEvents = eventShareRepository.findByEventId(eventId);

        if (sharedEvents.isEmpty()) {
            calendarEventRepository.deleteById(eventId);
        } else {
            if (transferOwnership) {
                User firstSharedUser = sharedEvents.get(0).getSharedUser();
                event.assignUser(firstSharedUser);
                calendarEventRepository.save(event);
                eventShareRepository.deleteAll(sharedEvents);
            } else {
                calendarEventRepository.deleteById(eventId);
                eventShareRepository.deleteAll(sharedEvents);
            }
        }
    }

    // 일정 공유
    public EventShare shareEvent(ShareEventRequestDTO requestDTO) {
        CalendarEvent event = calendarEventRepository.findById(requestDTO.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));
        User sharedByUser = userRepository.findById(requestDTO.getSharedByUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        User sharedUser = userRepository.findById(requestDTO.getSharedUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        EventShare eventShare = new EventShare(event, sharedByUser, sharedUser, LocalDateTime.now());
        return eventShareRepository.save(eventShare);
    }
}
