package api.ex_ss15_ioc.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private String status;
    private Integer code;
    private String message; // Có thể để null nếu không cần thiết
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status("success")
                .code(200)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(Integer code, String message, T data) {
        return ApiResponse.<T>builder()
                .status("success")
                .code(code)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(Integer code, String message) {
        return ApiResponse.<T>builder()
                .status("error")
                .code(code)
                .message(message)
                .build();
    }
}
