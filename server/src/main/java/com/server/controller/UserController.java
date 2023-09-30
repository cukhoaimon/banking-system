package com.server.controller;

import com.server.model.User;
import com.server.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    ResponseEntity<?> getUsers(@RequestParam(required = false) String username) {
        ResponseEntity<?> response = null;

        try {
            List<User> users = null;
            if (username == null) {
                users = new ArrayList<User>(userService.findAll());
            } else {
                Optional<User> user = userService.findByUsername(username);

                if (user.isPresent()) {
                    users = new ArrayList<User>();
                    users.add(user.get());
                }
            }

            if (users == null || users.isEmpty()) {
                response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                response = new ResponseEntity<>(users, HttpStatus.OK);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @GetMapping("/{id}")
    ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        Optional<User> data = userService.findById(id);

        return data.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }


    @PostMapping("register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User _user = userService.save(new User(user.getId(), user.getUsername(), user.getPass_hash()));
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable("id") String id) {
        try {
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
