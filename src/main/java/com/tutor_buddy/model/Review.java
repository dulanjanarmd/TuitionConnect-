package com.tutor_buddy.model;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Review {
    private Long id;
    private Long tutorId;
    private Long userId;
    private String tutorName;
    private String reviewText;
    private int rating;
    private LocalDateTime date;
}
