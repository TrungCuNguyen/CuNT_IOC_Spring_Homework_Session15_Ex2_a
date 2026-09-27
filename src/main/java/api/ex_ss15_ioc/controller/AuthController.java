package api.ex_ss15_ioc.controller;

import api.ex_ss15_ioc.dto.request.LoginRequest;
import api.ex_ss15_ioc.dto.request.RegisterRequest;
import api.ex_ss15_ioc.dto.response.ApiResponse;
import api.ex_ss15_ioc.dto.response.AuthResponse;
import api.ex_ss15_ioc.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody RegisterRequest request) {
        String result = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
