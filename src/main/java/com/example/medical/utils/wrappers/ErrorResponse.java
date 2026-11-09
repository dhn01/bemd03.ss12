package com.example.medical.utils.wrappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private String status;
    private String message;
    private LocalDateTime timestamp;

    public static ErrorResponse errorResponse(String status, String message) {
        return ErrorResponse.builder().status(status).message(message).timestamp(LocalDateTime.now()).build();
    }
}
