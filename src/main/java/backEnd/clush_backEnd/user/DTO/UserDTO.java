package backEnd.clush_backEnd.user.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;           // 유저 ID
    private String username;    // 유저 이름
    private String email;       // 이메일

    // 기본 생성자
    public UserDTO(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
