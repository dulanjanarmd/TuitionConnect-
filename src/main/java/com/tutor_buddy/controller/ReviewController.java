package com.tutor_buddy.controller;


import com.tutor_buddy.model.Review;
import com.tutor_buddy.service.ReviewService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/api/reviews")
    public class ReviewController {
        @Autowired
        private ReviewService reviewService;

        @PostMapping
        public Review submitReview(@RequestBody Review review) {
            return reviewService.submitReview(review);
        }


        @GetMapping("/file")
        public ResponseEntity<List<Review>> getReviewsFromFile() {
            List<Review> reviews = reviewService.readAllReviewsFromFile();
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        }
}

    

