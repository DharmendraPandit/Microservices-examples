package com.example.user;

import com.example.model.UserCart;
import com.example.model.UserCartException;
import com.example.model.UserCartMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Dharmendra Pandit.
 */
@RestController
public class UserCartController {

    @Autowired
    private UserCartService service;

    @Value("${user.username}")
    private String systemUsername;

    @Value("${user.password}")
    private String systemPassword;

    @ExceptionHandler(UserCartException.class)
    void handleUserException(UserCartException ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestHeader(value = "username") String username,
                                    @RequestHeader(value = "password") String password,
                                    @RequestBody UserCart userCart) {
        if(systemUsername.equalsIgnoreCase(username) && systemPassword.equals(password)) {
            return new ResponseEntity<>(service.update(username, userCart), HttpStatus.OK);
        } else {
            throw new UserCartException(HttpStatus.BAD_REQUEST.value(), UserCartMessage.USER_INVALID.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll(@RequestHeader(value = "username") String username,
                                    @RequestHeader(value = "password") String password) {
        if(systemUsername.equalsIgnoreCase(username) && systemPassword.equals(password)) {
            return new ResponseEntity<>(service.getAll(username), HttpStatus.OK);
        } else {
            throw new UserCartException(HttpStatus.BAD_REQUEST.value(), UserCartMessage.USER_INVALID.getMessage());
        }
    }
}
