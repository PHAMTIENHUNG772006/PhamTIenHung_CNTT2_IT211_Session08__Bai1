package com.re.session8.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiDataResponse<T>{
    private boolean success;
    private String message;
    private T data;
    private String error;
    private HttpStatus status;
}
