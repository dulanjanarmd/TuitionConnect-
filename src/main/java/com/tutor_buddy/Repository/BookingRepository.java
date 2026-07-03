package com.tutor_buddy.Repository;


import com.tutor_buddy.model.Booking;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

    @Repository
    public class BookingRepository {
        private final Map<Long, Booking> bookings = new HashMap<>();
        private long sequence = 1;

        public Booking save(Booking booking) {
            if (booking.getId() == null) {
                booking.setId(sequence++);
            }
            bookings.put(booking.getId(), booking);
            return booking;
        }

        public List<Booking> findByUserId(Long userId) {
            return bookings.values().stream()
                    .filter(booking -> booking.getUserId().equals(userId))
                    .collect(Collectors.toList());
        }

        public List<Booking> findByUserIdAndStatus(Long userId, String status) {
            return bookings.values().stream()
                    .filter(booking -> booking.getUserId().equals(userId) && booking.getStatus().equals(status))
                    .collect(Collectors.toList());
        }
    }

