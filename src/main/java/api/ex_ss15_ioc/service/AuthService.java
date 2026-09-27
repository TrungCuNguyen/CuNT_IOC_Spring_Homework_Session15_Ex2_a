package api.ex_ss15_ioc.service;

import api.ex_ss15_ioc.dto.request.LoginRequest;
import api.ex_ss15_ioc.dto.request.RegisterRequest;
import api.ex_ss15_ioc.dto.response.AuthResponse;
import api.ex_ss15_ioc.entity.User;
import api.ex_ss15_ioc.repository.UserRepository;
import api.ex_ss15_ioc.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        User user = User.builder()
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : "ROLE_USER")
                .build();

        userRepository.save(user);
        return "Đăng ký tài khoản thành công!";
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email hoặc mật khẩu không chính xác!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Email hoặc mật khẩu không chính xác!");
        }

        String accessToken = tokenProvider.generateAccessToken(user.getEmail());
        String refreshToken = tokenProvider.generateRefreshToken(user.getEmail());

        return new AuthResponse(accessToken, refreshToken);
    }
}
