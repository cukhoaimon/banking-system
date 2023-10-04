package com.server.controller;

import com.server.model.Customer;
import com.server.model.User;
import com.server.repository.UserRepository;
import com.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<?> getUser(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String value)
    {
        ResponseEntity<?> response = null;

        try {
            // query is null, return all users
            if (query == null) {
                List<User> users = new ArrayList<User>(userService.findAll());

                if (users.isEmpty()) {
                    response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    response = new ResponseEntity<>(users, HttpStatus.OK);
                }

                return response;
            }

            // query is not null
            if (value == null) {
                response = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

                return response;
            }

            Optional<User> user =  userService.findByUsername(value);

            if (user.isPresent()) {
                response = new ResponseEntity<>(user.get(), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return response;
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> processRegistration(@RequestBody User user) {
        User _user = userService
                .save(new User(user.getId(), user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getStringAuthorities()));

        return new ResponseEntity<>(_user, HttpStatus.CREATED);
    }


}
