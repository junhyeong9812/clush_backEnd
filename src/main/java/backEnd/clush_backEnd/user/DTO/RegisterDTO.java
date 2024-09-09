package backEnd.clush_backEnd.user.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String username;
    @NotEmpty(message = "회원 이메일은 필수입니다.")
    private String email;
    @NotEmpty(message = "회원 패스워드는 필수입니다.")
    private String password;

    public RegisterDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
