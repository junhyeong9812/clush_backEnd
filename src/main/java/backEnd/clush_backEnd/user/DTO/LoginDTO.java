package backEnd.clush_backEnd.user.DTO;

public class LoginDTO {
    private Long id;
    private String username;
    private String email;

    public LoginDTO(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
