package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.demo.controllers.auth.RegisterRequest;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackFor = Throwable.class)
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(rollbackFor = Throwable.class)
    public User updateUser(Long id, RegisterRequest userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setCountry(userDetails.getCountry());
            user.setEmail(userDetails.getEmail());
            user.setFirstName(userDetails.getFirstname());
            user.setLastName(userDetails.getLastname());
            user.setPassword(userDetails.getPassword());
            user.setUsername(userDetails.getUsername());
            user.setRole(userDetails.getRole());

            return userRepository.save(user);
        } else { // User no encontrado
            return null;
        }
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}