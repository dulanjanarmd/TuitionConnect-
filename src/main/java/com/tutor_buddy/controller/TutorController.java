package com.tutor_buddy.controller;

import com.tutor_buddy.model.Tutor;
import com.tutor_buddy.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutors")
public class TutorController {
    @Autowired
    private TutorService tutorService;

    @PostMapping("/register")
    public Tutor registerTutor(@RequestBody Tutor tutor) {
        return tutorService.registerTutor(tutor);
    }

    @GetMapping
    public List<Tutor> getAllTutors(@RequestParam(required = false) String subject,
                                    @RequestParam(required = false) String status,
                                    @RequestParam(required = false) String sortBy) {
        List<Tutor> tutors;
        if (subject != null) {
            tutors = tutorService.getTutorsBySubject(subject);
        } else if (status != null) {
            tutors = tutorService.getTutorsByStatus(status);
        } else {
            tutors = tutorService.getAllTutors();
        }

        if ("expertise".equals(sortBy)) {
            tutors = tutorService.sortTutorsByExpertise(tutors);
        } else if ("rating".equals(sortBy)) {
            tutors = tutorService.sortTutorsByRating(tutors);
        } else if ("experience".equals(sortBy)) {
            tutors = tutorService.sortTutorsByExperience(tutors);
        } else if ("experienceAndRating".equals(sortBy)) {
            tutors = tutorService.sortTutorsByExperienceAndRating(tutors);
        }
        
        return tutors;
    }

    @GetMapping("/{id}")
    public Tutor getTutorById(@PathVariable Long id) {
        return tutorService.getTutorById(id);
    }

    @PutMapping("/{id}")
    public Tutor updateTutor(@PathVariable Long id, @RequestBody Tutor tutor) {
        return tutorService.updateTutor(id, tutor);
    }

    @DeleteMapping("/{id}")
    public void deleteTutor(@PathVariable Long id) {
        tutorService.deleteTutor(id);
    }
    
    // Advanced filtering endpoint
    @GetMapping("/filter")
    public ResponseEntity<List<Tutor>> filterTutors(
            @RequestParam(required = false) Boolean hasExpertise,
            @RequestParam(required = false) Integer minExperienceYears,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) String sortBy) {
        
        List<Tutor> filteredTutors = tutorService.filterTutors(hasExpertise, minExperienceYears, minRating);
        
        if ("expertise".equals(sortBy)) {
            filteredTutors = tutorService.sortTutorsByExpertise(filteredTutors);
        } else if ("rating".equals(sortBy)) {
            filteredTutors = tutorService.sortTutorsByRating(filteredTutors);
        } else if ("experience".equals(sortBy)) {
            filteredTutors = tutorService.sortTutorsByExperience(filteredTutors);
        } else if ("experienceAndRating".equals(sortBy)) {
            filteredTutors = tutorService.sortTutorsByExperienceAndRating(filteredTutors);
        }
        
        return ResponseEntity.ok(filteredTutors);
    }
}