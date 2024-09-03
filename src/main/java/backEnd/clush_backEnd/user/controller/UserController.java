package backEnd.clush_backEnd.user.controller;


import backEnd.clush_backEnd.user.DTO.LoginDTO;
import backEnd.clush_backEnd.user.DTO.RegisterDTO;
import backEnd.clush_backEnd.user.entity.User;
import backEnd.clush_backEnd.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    // 회원 가입
    @PostMapping("/register")
    public ResponseEntity<Map<String, Boolean>> registerUser(@RequestBody RegisterDTO registerDTO) {
        Map<String, Boolean> response = new HashMap<>();
        try {
            userService.registerUser(registerDTO.getUsername(), registerDTO.getEmail(), registerDTO.getPassword());
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestParam String email, @RequestParam String password) {
        Optional<User> user = userService.login(email, password);
        if (user.isPresent()) {
            LoginDTO userDTO = new LoginDTO(user.get().getId(), user.get().getUsername(), user.get().getEmail());
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.status(401).build(); // Unauthorized
    }

    // 회원 탈퇴
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
