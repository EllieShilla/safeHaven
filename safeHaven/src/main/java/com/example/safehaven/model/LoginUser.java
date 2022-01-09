package com.example.safehaven.model;

import com.example.safehaven.dao.User;
import com.example.safehaven.repo.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginUser {
    public User IsLog(UserRepo userRepo) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userRepo.findByUsername(username);

        return user;
    }
}
