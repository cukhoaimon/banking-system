package com.server.service;

import com.server.model.User;
import com.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findAll()
                .stream()
                .filter(user -> Objects.equals(user.getUsername(), username))
                .findFirst();
    }
    public Integer findNumberOfUser() {
        List<User> users = userRepository.findAll();
        return users.size();
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
