package com.example.medical.utils.wrappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> response(String status, String message, T data) {
        return ApiResponse.<T>builder().status(status).message(message).data(data).build();
    }
}
