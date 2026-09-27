package api.ex_ss15_ioc.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    private String phone;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    private String role; // Mặc định có thể gán ROLE_USER
}
