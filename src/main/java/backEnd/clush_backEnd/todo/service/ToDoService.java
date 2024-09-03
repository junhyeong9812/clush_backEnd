package backEnd.clush_backEnd.todo.service;

import backEnd.clush_backEnd.todo.entity.ToDo;
import backEnd.clush_backEnd.todo.entity.ToDoStatus;
import backEnd.clush_backEnd.todo.repository.ToDoRepository;
import backEnd.clush_backEnd.user.entity.User;
import backEnd.clush_backEnd.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoService {
    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private UserRepository userRepository;

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

    // 할 일 조회 (특정 사용자 기준)
    public List<ToDo> getToDosByUserId(Long userId) {
        return toDoRepository.findByUserId(userId);
    }

    // 할 일 수정
    public ToDo updateToDo(Long toDoId, ToDoStatus status) {
        Optional<ToDo> toDoOptional = toDoRepository.findById(toDoId);
        if (toDoOptional.isPresent()) {
            ToDo toDo = toDoOptional.get();
            toDo.setStatus(status);
            return toDoRepository.save(toDo);
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

    // 할 일 단일 조회
    public Optional<ToDo> getToDoById(Long toDoId) {
        return toDoRepository.findById(toDoId);
    }
}
