package com.tutor_buddy.service;

import com.tutor_buddy.model.Subject;
import com.tutor_buddy.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }
    
    public Subject getSubjectByCode(String code) {
        return subjectRepository.findByCode(code);
    }
    
    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id);
    }
}