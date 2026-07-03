package com.tutor_buddy.model;

import lombok.Data;

@Data
public class Subject {
    private Long id;
    private String code;
    private String name;
    private String description;
    
    public Subject(Long id, String code, String description) {
        this.id = id;
        this.code = code;
        
        if (description != null && description.contains(" ")) {
            int firstSpaceIndex = description.indexOf(' ');
            this.name = description.substring(0, firstSpaceIndex);
            this.description = description;
        } else {
            this.name = code;
            this.description = description;
        }
    }
    
    public Subject() {
    }
}