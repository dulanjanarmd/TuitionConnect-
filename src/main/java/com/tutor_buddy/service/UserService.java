package com.tutor_buddy.service;

import com.tutor_buddy.model.User;
import com.tutor_buddy.model.UserBST;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    private UserBST userBST;

    public UserService() {
        this.userBST = new UserBST();
    }

    public User registerStudent(User user) {
        user.setRole("student");
        user.setJoinedDate(LocalDateTime.now());
        userBST.insert(user);
        return user;
    }

    public User getUserById(Long id) {
        User user = userBST.find(id);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userBST.getAllUsers();
    }

    public User updateUser(Long id, User updatedUser) {
        User user = userBST.find(id);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        user.setFullName(updatedUser.getFullName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());
        user.setBio(updatedUser.getBio());
        user.setSubjectsTaught(updatedUser.getSubjectsTaught());
        user.setProfilePicture(updatedUser.getProfilePicture());
        userBST.update(user);
        return user;
    }

    public void deleteUser(Long id) {
        userBST.delete(id);
    }

    public List<User> getTutors() {
        return userBST.getUsersByRole("tutor");
    }
}