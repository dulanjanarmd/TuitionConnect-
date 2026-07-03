package com.tutor_buddy.Repository;



import com.tutor_buddy.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

    @Repository
    public class UserRepository {
        private Map<Long, User> users = new HashMap<>();
        private long sequence = 1;

        public User save(User user) {
            if (user.getId() == null) {
                user.setId(sequence++);
            }
            users.put(user.getId(), user);
            return user;
        }

        public User findById(Long id) {
            return users.get(id);
        }

        public List<User> findAll() {
            return new ArrayList<>(users.values());
        }

        public void delete(Long id) {
            users.remove(id);
        }

        public User findByEmailAndPassword(String email, String password) {
            return users.values().stream()
                    .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                    .findFirst()
                    .orElse(null);
        }

        public List<User> findByRole(String role) {
            return users.values().stream()
                    .filter(user -> user.getRole().equals(role))
                    .collect(Collectors.toList());
        }
    }

