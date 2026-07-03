package com.tutor_buddy.Repository;


import com.tutor_buddy.model.Review;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

    @Repository
    public class ReviewRepository {
        private Map<Long, Review> reviews = new HashMap<>();
        private long sequence = 1;

        public Review save(Review review) {
            if (review.getId() == null) {
                review.setId(sequence++);
            }
            reviews.put(review.getId(), review);
            return review;
        }
    }

