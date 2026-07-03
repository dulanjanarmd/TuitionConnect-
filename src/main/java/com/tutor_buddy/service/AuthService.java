package com.tutor_buddy.service;

import com.tutor_buddy.model.User;
import com.tutor_buddy.model.Tutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    
    @Autowired
    private TutorService tutorService;

    public Map<String, Object> login(String email, String password) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            User user = findUserByEmail(email);
            
            Tutor tutor = findTutorByEmail(email);
            
            if (user != null && "student".equals(user.getRole()) && 
                password != null && password.equals(user.getPassword())) {
                response.put("success", true);
                response.put("role", "student");
                response.put("data", user);
                return response;
            }
            
            if (tutor != null && password != null && tutor.getPassword() != null && 
                password.equals(tutor.getPassword())) {
                response.put("success", true);
                response.put("role", "tutor");
                response.put("data", tutor);
                return response;
            }
            
            response.put("success", false);
            response.put("message", "Invalid email or password");
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Authentication error: " + e.getMessage());
            return response;
        }
    }
    
    private User findUserByEmail(String email) {
        if (email == null) return null;
        
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            if (user != null && email.equals(user.getEmail())) {
                return user;
            }
        }
        return null;
    }
    
    private Tutor findTutorByEmail(String email) {
        if (email == null) return null;
        
        List<Tutor> tutors = tutorService.getAllTutors();
        for (Tutor tutor : tutors) {
            if (tutor != null && email.equals(tutor.getEmail())) {
                return tutor;
            }
        }
        return null;
    }
}