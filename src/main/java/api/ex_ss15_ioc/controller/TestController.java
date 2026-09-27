package api.ex_ss15_ioc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping
    public ResponseEntity<String> testProtectedEndpoint() {
        return ResponseEntity.ok("Truy cập endpoint bảo mật thành công!");
    }
}
