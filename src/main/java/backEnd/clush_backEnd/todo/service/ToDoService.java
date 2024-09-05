package backEnd.clush_backEnd.todo.service;

import backEnd.clush_backEnd.todo.DTO.SelectToDoDTO;
import backEnd.clush_backEnd.todo.entity.ToDo;
import backEnd.clush_backEnd.todo.entity.ToDoStatus;
import backEnd.clush_backEnd.todo.repository.ToDoRepository;
import backEnd.clush_backEnd.user.entity.User;
import backEnd.clush_backEnd.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;
    private final UserRepository userRepository;

    // 할 일 생성
    public ToDo createToDo(Long userId, String title, String description) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            ToDo toDo = new ToDo(
                    title,//제목
                    description,//상세내용
                    user,//작성자
                    LocalDateTime.now()//작성일
            );
            return toDoRepository.save(toDo);
        } else {
            throw new RuntimeException("사용자가 없습니다.");
        }
    }



    // 할 일 수정
    public Long updateToDo(Long toDoId, ToDoStatus status) {
        Optional<ToDo> toDoOptional = toDoRepository.findById(toDoId);
        if (toDoOptional.isPresent()) {
            ToDo toDo = toDoOptional.get();
            toDo.setStatus(status);
            return toDo.getId();
        } else {
            throw new RuntimeException("투두 목록이 없습니다.");
        }
    }

    // 할 일 삭제
    public void deleteToDo(Long toDoId) {
        if (toDoRepository.existsById(toDoId)) {
            toDoRepository.deleteById(toDoId);
        } else {
            throw new RuntimeException("투두 목록이 없습니다.");
        }
    }

    // 할 일 조회 (특정 사용자 기준, DTO로 변환)
    @Transactional(readOnly = true)
    public List<SelectToDoDTO> getToDosByUserId(Long userId) {
        List<ToDo> toDos = toDoRepository.findByUserId(userId);

        // 서비스에서 변환 후 반환
        return toDos.stream()
                .map(this::convertToDTO)  // 엔티티 -> DTO 변환
                .collect(Collectors.toList());
    }

    // 할 일 단일 조회 (DTO로 변환)
    @Transactional(readOnly = true)
    public Optional<SelectToDoDTO> getToDoById(Long toDoId) {
        return toDoRepository.findById(toDoId)
                .map(this::convertToDTO);  // 엔티티 -> DTO 변환
    }

    //엔티티 DTO로 변환
    private SelectToDoDTO convertToDTO(ToDo toDo) {
        List<ToDoStatus> statusOptions = List.of(ToDoStatus.values());
        return new SelectToDoDTO(
                toDo.getId(),
                toDo.getTitle(),
                toDo.getDescription(),
                toDo.getStatus(),
                toDo.getUser().getId(),
                toDo.getUser().getUsername(),
                toDo.getWriteDate(),
                statusOptions
        );
    }
}
