package io.berbotworks.mc.controllers;

import javax.servlet.ServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.berbotworks.mc.exceptions.UserAlreadyExistsException;
import io.berbotworks.mc.models.User;
import io.berbotworks.mc.security.JWT;
import io.berbotworks.mc.services.UserService;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = {
        "http://localhost:3000, https://ft-s.herokuapp.com/" }, allowedHeaders = "*", exposedHeaders = {
                "Authorization", "Access-Control-Allow-Origin", "X-Room-Id" }, maxAge = 10000l)
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> newUser(@RequestBody User user) {
        ResponseEntity<?> responseEntity = null;
        User savedUser = null;
        try {
            savedUser = userService.saveUser(user);
            String jwt = JWT.generateToken(savedUser);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + jwt);
            httpHeaders.add("X-Room-Id", savedUser.getRoomId());
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).build();
        } catch (UserAlreadyExistsException e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @PostMapping("/login")
    public ResponseEntity<?> logIn(ServletRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Room-Id", (String) request.getAttribute("roomId"));
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).build();
    }
}
