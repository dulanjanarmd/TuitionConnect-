package com.tutor_buddy.model;

import com.tutor_buddy.model.User;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserBST {
    private Node root;
    private static final String FILE_PATH = "C:\\Users\\damin\\OneDrive\\Documents\\tutor_buddy\\tutor_buddy\\users.txt";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private class Node {
        User user;
        Node left;
        Node right;

        Node(User user) {
            this.user = user;
            this.left = null;
            this.right = null;
        }
    }

    public UserBST() {
        this.root = null;
        File file = new File(FILE_PATH);
        File directory = file.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        loadFromFile(); 
    }

    // Insert a user into the BST
    public void insert(User user) {
        root = insertRec(root, user);
        saveToFile(); 
    }

    private Node insertRec(Node root, User user) {
        if (root == null) {
            root = new Node(user);
            return root;
        }

        if (user.getId() < root.user.getId()) {
            root.left = insertRec(root.left, user);
        } else if (user.getId() > root.user.getId()) {
            root.right = insertRec(root.right, user);
        } else {
            root.user = user;
        }

        return root;
    }

    // Find a user by ID
    public User find(Long id) {
        Node node = findRec(root, id);
        return node != null ? node.user : null;
    }

    private Node findRec(Node root, Long id) {
        if (root == null || root.user.getId().equals(id)) {
            return root;
        }

        if (id < root.user.getId()) {
            return findRec(root.left, id);
        }

        return findRec(root.right, id);
    }

    // Delete a user by ID
    public void delete(Long id) {
        root = deleteRec(root, id);
        saveToFile(); 
    }

    private Node deleteRec(Node root, Long id) {
        if (root == null) {
            return root;
        }

        if (id < root.user.getId()) {
            root.left = deleteRec(root.left, id);
        } else if (id > root.user.getId()) {
            root.right = deleteRec(root.right, id);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            User minUser = minValue(root.right);
            root.user.setId(minUser.getId());
            root.user.setFullName(minUser.getFullName());
            root.user.setEmail(minUser.getEmail());
            root.user.setPhone(minUser.getPhone());
            root.user.setRole(minUser.getRole());
            root.user.setJoinedDate(minUser.getJoinedDate());
            root.user.setBio(minUser.getBio());
            root.user.setSubjectsTaught(minUser.getSubjectsTaught());
            root.user.setPassword(minUser.getPassword());
            root.user.setProfilePicture(minUser.getProfilePicture());

            root.right = deleteRec(root.right, minUser.getId());
        }

        return root;
    }

    private User minValue(Node root) {
        User minv = root.user;
        while (root.left != null) {
            minv = root.left.user;
            root = root.left;
        }
        return minv;
    }

    // Update a user
    public void update(User user) {
        delete(user.getId());
        insert(user);
    }

    // Get all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        inOrderTraversal(root, users);
        return users;
    }

    private void inOrderTraversal(Node node, List<User> users) {
        if (node != null) {
            inOrderTraversal(node.left, users);
            users.add(node.user);
            inOrderTraversal(node.right, users);
        }
    }

    // Get users by role
    public List<User> getUsersByRole(String role) {
        List<User> allUsers = getAllUsers();
        List<User> filteredUsers = new ArrayList<>();
        
        for (User user : allUsers) {
            if (user.getRole().equals(role)) {
                filteredUsers.add(user);
            }
        }
        
        return filteredUsers;
    }

    // Save the BST to file in a structured text format
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            List<User> users = getAllUsers();
            for (User user : users) {
                writer.write("BEGIN_USER");
                writer.newLine();
                writer.write("ID:" + user.getId());
                writer.newLine();
                writer.write("FULL_NAME:" + (user.getFullName() != null ? user.getFullName() : ""));
                writer.newLine();
                writer.write("EMAIL:" + (user.getEmail() != null ? user.getEmail() : ""));
                writer.newLine();
                writer.write("PHONE:" + (user.getPhone() != null ? user.getPhone() : ""));
                writer.newLine();
                writer.write("ROLE:" + (user.getRole() != null ? user.getRole() : ""));
                writer.newLine();
                writer.write("JOINED_DATE:" + (user.getJoinedDate() != null ? user.getJoinedDate().format(DATE_TIME_FORMATTER) : ""));
                writer.newLine();
                writer.write("BIO:" + (user.getBio() != null ? user.getBio() : ""));
                writer.newLine();
                writer.write("SUBJECTS_TAUGHT:" + (user.getSubjectsTaught() != null ? user.getSubjectsTaught() : ""));
                writer.newLine();
                writer.write("PASSWORD:" + (user.getPassword() != null ? user.getPassword() : ""));
                writer.newLine();
                writer.write("PROFILE_PICTURE:" + (user.getProfilePicture() != null ? user.getProfilePicture() : ""));
                writer.newLine();
                writer.write("END_USER");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load users from file
    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            User currentUser = null;
            
            while ((line = reader.readLine()) != null) {
                if (line.equals("BEGIN_USER")) {
                    currentUser = new User();
                } else if (line.equals("END_USER") && currentUser != null) {
                    root = insertRec(root, currentUser);
                    currentUser = null;
                } else if (currentUser != null && line.contains(":")) {
                    String[] parts = line.split(":", 2);
                    String key = parts[0];
                    String value = parts.length > 1 ? parts[1] : "";
                    
                    switch (key) {
                        case "ID":
                            currentUser.setId(Long.parseLong(value));
                            break;
                        case "FULL_NAME":
                            currentUser.setFullName(value);
                            break;
                        case "EMAIL":
                            currentUser.setEmail(value);
                            break;
                        case "PHONE":
                            currentUser.setPhone(value);
                            break;
                        case "ROLE":
                            currentUser.setRole(value);
                            break;
                        case "JOINED_DATE":
                            currentUser.setJoinedDate(value.isEmpty() ? null : LocalDateTime.parse(value, DATE_TIME_FORMATTER));
                            break;
                        case "BIO":
                            currentUser.setBio(value);
                            break;
                        case "SUBJECTS_TAUGHT":
                            currentUser.setSubjectsTaught(value);
                            break;
                        case "PASSWORD":
                            currentUser.setPassword(value);
                            break;
                        case "PROFILE_PICTURE":
                            currentUser.setProfilePicture(value);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}