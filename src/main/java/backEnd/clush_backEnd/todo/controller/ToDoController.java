package backEnd.clush_backEnd.todo.controller;

import backEnd.clush_backEnd.todo.DTO.ModifyToDoDTO;
import backEnd.clush_backEnd.todo.DTO.SelectToDoDTO;
import backEnd.clush_backEnd.todo.DTO.ToDoDTO;
import backEnd.clush_backEnd.todo.entity.ToDo;
import backEnd.clush_backEnd.todo.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService toDoService;

    // 할 일 생성
    @PostMapping
    public ResponseEntity<Map<String, Boolean>> createToDo(@RequestBody ToDoDTO toDoRequest) {
        Map<String, Boolean> response = new HashMap<>();
        try {
            toDoService.createToDo(toDoRequest.getUserId(), toDoRequest.getTitle(), toDoRequest.getDescription());
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // 특정 사용자의 모든 할 일 조회 (DTO로 반환)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SelectToDoDTO>> getToDosByUserId(@PathVariable Long userId) {
        List<SelectToDoDTO> toDoDTOs = toDoService.getToDosByUserId(userId);
        return ResponseEntity.ok(toDoDTOs);
    }

    // 특정 할 일 단일 조회 (DTO로 반환)
    @GetMapping("/{toDoId}")
    public ResponseEntity<SelectToDoDTO> getToDoById(@PathVariable Long toDoId) {
        Optional<SelectToDoDTO> toDoDTO = toDoService.getToDoById(toDoId);
        return toDoDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

//    // 할 일 수정
//    @PutMapping("/{toDoId}")
//    public ResponseEntity<Map<String, Boolean>> updateToDo(@RequestBody ModifyToDoDTO toDoRequest) {
//        Map<String, Boolean> response = new HashMap<>();
//        try {
//            toDoService.updateToDo(toDoRequest.getUserId(), toDoRequest.getStatus());
//            response.put("success", true);
//            return ResponseEntity.ok(response);
//        } catch (RuntimeException e) {
//            response.put("success", false);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }
//    }
// 할 일 수정 (title, description, status 모두 업데이트)
@PutMapping("/{toDoId}")
public ResponseEntity<Map<String, Boolean>> updateToDo(@PathVariable Long toDoId, @RequestBody ModifyToDoDTO toDoRequest) {
    Map<String, Boolean> response = new HashMap<>();
    try {
        // 서비스 호출 (title, description, status 모두 업데이트)
        toDoService.updateToDo(toDoId, toDoRequest.getTitle(), toDoRequest.getDescription(), toDoRequest.getStatus());
        response.put("success", true);
        return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
        response.put("success", false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

    // 할 일 삭제
    @DeleteMapping("/{toDoId}")
    public ResponseEntity<Map<String, Boolean>> deleteToDo(@PathVariable Long toDoId) {
        Map<String, Boolean> response = new HashMap<>();
        try {
            toDoService.deleteToDo(toDoId);
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
