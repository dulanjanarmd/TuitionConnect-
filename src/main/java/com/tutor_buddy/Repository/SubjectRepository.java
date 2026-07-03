package com.tutor_buddy.Repository;

import com.tutor_buddy.model.Subject;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SubjectRepository {
    private static final String FILE_PATH = "C:\\Users\\damin\\OneDrive\\Documents\\tutor_buddy\\tutor_buddy\\subjects.txt";
    private Map<String, Subject> subjectsMap = new HashMap<>();
    private List<Subject> subjectsList = new ArrayList<>();
    
    public SubjectRepository() {
        loadSubjectsFromFile();
    }
    
    private void loadSubjectsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            String currentCode = null;
            StringBuilder currentDescription = new StringBuilder();
            long id = 1;
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; 
                }
                
                if (line.length() <= 10 && line.toUpperCase().equals(line)) { 
                    if (currentCode != null) {
                        Subject subject = new Subject(id++, currentCode, currentDescription.toString().trim());
                        subjectsMap.put(currentCode, subject);
                        subjectsList.add(subject);
                        currentDescription = new StringBuilder();
                    }
                    currentCode = line.trim();
                } else {
                    currentDescription.append(line).append(" ");
                }
            }
            
            if (currentCode != null) {
                Subject subject = new Subject(id, currentCode, currentDescription.toString().trim());
                subjectsMap.put(currentCode, subject);
                subjectsList.add(subject);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public List<Subject> findAll() {
        return subjectsList;
    }
    
    public Subject findByCode(String code) {
        return subjectsMap.get(code);
    }
    
    public Subject findById(Long id) {
        for (Subject subject : subjectsList) {
            if (subject.getId().equals(id)) {
                return subject;
            }
        }
        return null;
    }
}