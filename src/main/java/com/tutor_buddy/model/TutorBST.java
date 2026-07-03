package com.tutor_buddy.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TutorBST {
    private Node root;
    private static final String FILE_PATH = "C:\\Users\\damin\\OneDrive\\Documents\\tutor_buddy\\tutor_buddy\\tutors.txt";

    private class Node {
        Tutor tutor;
        Node left;
        Node right;

        Node(Tutor tutor) {
            this.tutor = tutor;
            this.left = null;
            this.right = null;
        }
    }

    public TutorBST() {
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

    // Insert a tutor into the BST
    public void insert(Tutor tutor) {
        root = insertRec(root, tutor);
        saveToFile(); 
    }

    private Node insertRec(Node root, Tutor tutor) {
        if (root == null) {
            root = new Node(tutor);
            return root;
        }

        if (tutor.getId() < root.tutor.getId()) {
            root.left = insertRec(root.left, tutor);
        } else if (tutor.getId() > root.tutor.getId()) {
            root.right = insertRec(root.right, tutor);
        } else {
            root.tutor = tutor;
        }

        return root;
    }

    // Find a tutor by ID
    public Tutor find(Long id) {
        Node node = findRec(root, id);
        return node != null ? node.tutor : null;
    }

    private Node findRec(Node root, Long id) {
        if (root == null || root.tutor.getId().equals(id)) {
            return root;
        }

        if (id < root.tutor.getId()) {
            return findRec(root.left, id);
        }

        return findRec(root.right, id);
    }

    // Delete a tutor by ID
    public void delete(Long id) {
        root = deleteRec(root, id);
        saveToFile(); 
    }

    private Node deleteRec(Node root, Long id) {
        if (root == null) {
            return root;
        }

        if (id < root.tutor.getId()) {
            root.left = deleteRec(root.left, id);
        } else if (id > root.tutor.getId()) {
            root.right = deleteRec(root.right, id);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            Tutor minTutor = minValue(root.right);
            root.tutor.setId(minTutor.getId());
            root.tutor.setFullName(minTutor.getFullName());
            root.tutor.setEmail(minTutor.getEmail());
            root.tutor.setPhone(minTutor.getPhone());
            root.tutor.setHourlyRate(minTutor.getHourlyRate());
            root.tutor.setBio(minTutor.getBio());
            root.tutor.setEducation(minTutor.getEducation());
            root.tutor.setExperienceYears(minTutor.getExperienceYears());
            root.tutor.setSubjects(minTutor.getSubjects());
            root.tutor.setAvailability(minTutor.getAvailability());
            root.tutor.setStatus(minTutor.getStatus());
            root.tutor.setProfilePicture(minTutor.getProfilePicture());
            root.tutor.setRating(minTutor.getRating());
            root.tutor.setSubjectExpertise(minTutor.getSubjectExpertise());
            root.tutor.setPassword(minTutor.getPassword()); 

            root.right = deleteRec(root.right, minTutor.getId());
        }

        return root;
    }

    private Tutor minValue(Node root) {
        Tutor minv = root.tutor;
        while (root.left != null) {
            minv = root.left.tutor;
            root = root.left;
        }
        return minv;
    }

    // Update a tutor
    public void update(Tutor tutor) {
        delete(tutor.getId());
        insert(tutor);
    }

    // Get all tutors
    public List<Tutor> getAllTutors() {
        List<Tutor> tutors = new ArrayList<>();
        inOrderTraversal(root, tutors);
        return tutors;
    }

    private void inOrderTraversal(Node node, List<Tutor> tutors) {
        if (node != null) {
            inOrderTraversal(node.left, tutors);
            tutors.add(node.tutor);
            inOrderTraversal(node.right, tutors);
        }
    }

    // Get tutors by subject
    public List<Tutor> getTutorsBySubject(String subject) {
        List<Tutor> allTutors = getAllTutors();
        List<Tutor> filteredTutors = new ArrayList<>();
        
        for (Tutor tutor : allTutors) {
            if (tutor.getSubjects() != null && tutor.getSubjects().contains(subject)) {
                filteredTutors.add(tutor);
            }
        }
        
        return filteredTutors;
    }

    // Get tutors by status
    public List<Tutor> getTutorsByStatus(String status) {
        List<Tutor> allTutors = getAllTutors();
        List<Tutor> filteredTutors = new ArrayList<>();
        
        for (Tutor tutor : allTutors) {
            if (tutor.getStatus() != null && tutor.getStatus().equals(status)) {
                filteredTutors.add(tutor);
            }
        }
        
        return filteredTutors;
    }

    // Save the BST to file in a structured text format
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            List<Tutor> tutors = getAllTutors();
            for (Tutor tutor : tutors) {
                writer.write("BEGIN_TUTOR");
                writer.newLine();
                writer.write("ID:" + tutor.getId());
                writer.newLine();
                writer.write("FULL_NAME:" + (tutor.getFullName() != null ? tutor.getFullName() : ""));
                writer.newLine();
                writer.write("EMAIL:" + (tutor.getEmail() != null ? tutor.getEmail() : ""));
                writer.newLine();
                writer.write("PHONE:" + (tutor.getPhone() != null ? tutor.getPhone() : ""));
                writer.newLine();
                writer.write("HOURLY_RATE:" + tutor.getHourlyRate());
                writer.newLine();
                writer.write("BIO:" + (tutor.getBio() != null ? tutor.getBio() : ""));
                writer.newLine();
                writer.write("EDUCATION:" + (tutor.getEducation() != null ? tutor.getEducation() : ""));
                writer.newLine();
                writer.write("EXPERIENCE_YEARS:" + tutor.getExperienceYears());
                writer.newLine();
                writer.write("SUBJECTS:" + (tutor.getSubjects() != null ? String.join("|", tutor.getSubjects()) : ""));
                writer.newLine();
                writer.write("RATING:" + tutor.getRating());
                writer.newLine();
                
                // Write availability list
                writer.write("BEGIN_AVAILABILITY");
                writer.newLine();
                if (tutor.getAvailability() != null) {
                    for (Availability availability : tutor.getAvailability()) {
                        writer.write("DAY:" + (availability.getDay() != null ? availability.getDay() : ""));
                        writer.newLine();
                        writer.write("START_TIME:" + (availability.getStartTime() != null ? availability.getStartTime() : ""));
                        writer.newLine();
                        writer.write("END_TIME:" + (availability.getEndTime() != null ? availability.getEndTime() : ""));
                        writer.newLine();
                    }
                }
                writer.write("END_AVAILABILITY");
                writer.newLine();
                
                writer.write("STATUS:" + (tutor.getStatus() != null ? tutor.getStatus() : ""));
                writer.newLine();
                writer.write("PROFILE_PICTURE:" + (tutor.getProfilePicture() != null ? tutor.getProfilePicture() : ""));
                writer.newLine();
                writer.write("SUBJECT_EXPERTISE:" + tutor.getSubjectExpertise());
                writer.newLine();
                writer.write("PASSWORD:" + (tutor.getPassword() != null ? tutor.getPassword() : ""));
                writer.newLine();
                writer.write("END_TUTOR");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load tutors from file
    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            Tutor currentTutor = null;
            boolean inAvailability = false;
            Availability currentAvailability = null;
            
            while ((line = reader.readLine()) != null) {
                if (line.equals("BEGIN_TUTOR")) {
                    currentTutor = new Tutor();
                    currentTutor.setAvailability(new ArrayList<>());
                } else if (line.equals("END_TUTOR") && currentTutor != null) {
                    root = insertRec(root, currentTutor);
                    currentTutor = null;
                } else if (line.equals("BEGIN_AVAILABILITY")) {
                    inAvailability = true;
                } else if (line.equals("END_AVAILABILITY")) {
                    inAvailability = false;
                } else if (inAvailability && currentTutor != null) {
                    if (line.startsWith("DAY:")) {
                        currentAvailability = new Availability();
                        currentAvailability.setDay(line.substring(4));
                    } else if (line.startsWith("START_TIME:") && currentAvailability != null) {
                        currentAvailability.setStartTime(line.substring(11));
                    } else if (line.startsWith("END_TIME:") && currentAvailability != null) {
                        currentAvailability.setEndTime(line.substring(9));
                        currentTutor.getAvailability().add(currentAvailability);
                        currentAvailability = null;
                    }
                } else if (currentTutor != null && line.contains(":")) {
                    String[] parts = line.split(":", 2);
                    String key = parts[0];
                    String value = parts.length > 1 ? parts[1] : "";
                    
                    switch (key) {
                        case "ID":
                            currentTutor.setId(Long.parseLong(value));
                            break;
                        case "FULL_NAME":
                            currentTutor.setFullName(value);
                            break;
                        case "EMAIL":
                            currentTutor.setEmail(value);
                            break;
                        case "PHONE":
                            currentTutor.setPhone(value);
                            break;
                        case "HOURLY_RATE":
                            currentTutor.setHourlyRate(Double.parseDouble(value));
                            break;
                        case "BIO":
                            currentTutor.setBio(value);
                            break;
                        case "EDUCATION":
                            currentTutor.setEducation(value);
                            break;
                        case "EXPERIENCE_YEARS":
                            currentTutor.setExperienceYears(Integer.parseInt(value));
                            break;
                        case "SUBJECTS":
                            currentTutor.setSubjects(value.isEmpty() ? List.of() : List.of(value.split("\\|")));
                            break;
                        case "RATING":
                            currentTutor.setRating(Double.parseDouble(value));
                            break;
                        case "STATUS":
                            currentTutor.setStatus(value);
                            break;
                        case "PROFILE_PICTURE":
                            currentTutor.setProfilePicture(value);
                            break;
                        case "SUBJECT_EXPERTISE":
                            currentTutor.setSubjectExpertise(Boolean.parseBoolean(value));
                            break;
                        case "PASSWORD":
                            currentTutor.setPassword(value);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}