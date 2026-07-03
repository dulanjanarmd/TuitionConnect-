package com.tutor_buddy.model;

import lombok.Data;
import java.util.List;

@Data
public class Tutor {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private double hourlyRate;
    private String bio;
    private String education;
    private int experienceYears;
    private List<String> subjects;
    private List<Availability> availability;
    private String status; // "active", "inactive"
    private String profilePicture;
    private double rating;
    private boolean subjectExpertise;
    private String password; 

    public boolean getSubjectExpertise() {
        return subjectExpertise;
    }

    public void setSubjectExpertise(boolean subjectExpertise) {
        this.subjectExpertise = subjectExpertise;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

@Data
class Availability {
    private String day;
    private String startTime;
    private String endTime;
}