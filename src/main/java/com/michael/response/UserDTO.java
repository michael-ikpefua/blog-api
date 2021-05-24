package com.michael.response;

import com.michael.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String message;
    private User user;
    private HttpStatus status;


}
