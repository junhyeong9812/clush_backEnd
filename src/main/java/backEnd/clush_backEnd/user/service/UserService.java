package backEnd.clush_backEnd.user.service;

import backEnd.clush_backEnd.user.entity.User;
import backEnd.clush_backEnd.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원 가입
    public User registerUser(String username, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("이미 사용 중인 이메일입니다.");
        }
        User user = new User(username, email, password);
        return userRepository.save(user);
    }

    // 로그인
    public Optional<User> login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }
    // 회원 탈퇴
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    // 회원 단건 조회
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    // 회원 전체 조회
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
