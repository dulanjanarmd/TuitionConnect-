package com.tutor_buddy.service;


import com.tutor_buddy.model.Review;
import com.tutor_buddy.model.Tutor;
import com.tutor_buddy.Repository.ReviewRepository;
import com.tutor_buddy.Repository.TutorRepository;
import com.tutor_buddy.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

    @Service
    public class ReviewService {
        @Autowired
        private ReviewRepository reviewRepository;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private TutorRepository tutorRepository;

        
        public Review submitReview(Review review) {
            // if (userRepository.findById(review.getUserId()) == null) {
            //     throw new RuntimeException("User not found");
            // }
            // Tutor tutor = tutorRepository.findAll().stream()
            //         .filter(t -> t.getFullName().equals(review.getTutorName()))
            //         .findFirst()
            //         .orElseThrow(() -> new RuntimeException("Tutor not found"));
            // review.setTutorId(tutor.getId());
            review.setDate(LocalDateTime.now());
            Review savedReview = reviewRepository.save(review);
            writeReviewToFile(savedReview);
            return reviewRepository.save(review);
        }

        private void writeReviewToFile(Review review) {
    String fileName = "reviews.txt"; 
    String content = String.format(
        "Review ID: %d%nUser ID: %d%nTutor ID: %d%nTutor Name: %s%nRating: %d%nDate: %s%nReview: %s%n%n",
        review.getId(),
        review.getUserId(),
        review.getTutorId(),
        review.getTutorName(),
        review.getRating(),
        review.getDate(),
        review.getReviewText()
    );

    try (FileWriter writer = new FileWriter(fileName, true)) {
        writer.write(content);
    } catch (IOException e) {
        e.printStackTrace();
    }

    }

    public List<Review> readAllReviewsFromFile() {
        List<Review> reviews = new ArrayList<>();
        String fileName = "reviews.txt";
    
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Review review = new Review();
    
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("Review ID:")) {
                    review = new Review();
                    review.setId(Long.parseLong(line.split(":")[1].trim()));
                } else if (line.startsWith("User ID:")) {
                    review.setUserId(Long.parseLong(line.split(":")[1].trim()));
                } else if (line.startsWith("Tutor ID:")) {
                    review.setTutorId(Long.parseLong(line.split(":")[1].trim()));
                } else if (line.startsWith("Tutor Name:")) {
                    review.setTutorName(line.split(":", 2)[1].trim());
                } else if (line.startsWith("Rating:")) {
                    review.setRating(Integer.parseInt(line.split(":")[1].trim()));
                } else if (line.startsWith("Date:")) {
                    review.setDate(java.time.LocalDateTime.parse(line.split(":", 2)[1].trim()));
                } else if (line.startsWith("Review:")) {
                    review.setReviewText(line.split(":", 2)[1].trim());
                } else if (line.isEmpty()) {
                    reviews.add(review);
                }
            }
    
            if (review.getId() != null && !reviews.contains(review)) {
                reviews.add(review);
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return reviews;
    }
}
