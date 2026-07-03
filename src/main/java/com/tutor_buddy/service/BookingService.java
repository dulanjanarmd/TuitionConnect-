package com.tutor_buddy.service;

import com.tutor_buddy.model.Booking;
import com.tutor_buddy.model.BookingBST;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookingService {
    private BookingBST bookingBST = new BookingBST();
    private AtomicLong idCounter = new AtomicLong(System.currentTimeMillis());

    public Booking createBooking(Booking booking) {
        booking.setId(idCounter.getAndIncrement());
        booking.setStatus("upcoming");
        bookingBST.insert(booking);
        return booking;
    }

    public List<Booking> getAllBookings() {
        return bookingBST.getAllBookings();
    }

    public List<Booking> getBookingsByUser(Long userId) {
        return bookingBST.findByUser(userId);
    }

    public List<Booking> getBookingsByUserAndStatus(Long userId, String status) {
        return bookingBST.findByUserAndStatus(userId, status);
    }
    
    public List<Booking> getBookingsByTutor(Long tutorId) {
        return bookingBST.findByTutor(tutorId);
    }
    
    public List<Booking> getUpcomingBookings() {
        return bookingBST.findUpcomingBookings();
    }

    public Booking getBookingById(Long id) {
        Booking booking = bookingBST.find(id);
        if (booking == null) throw new RuntimeException("Booking not found");
        return booking;
    }

    public Booking updateBooking(Long id, Booking updatedBooking) {
        Booking existing = bookingBST.find(id);
        if (existing == null) throw new RuntimeException("Booking not found");

        updatedBooking.setId(id);
        bookingBST.update(updatedBooking);
        return updatedBooking;
    }
    
    public Booking updateBookingStatus(Long id, String status) {
        Booking booking = bookingBST.find(id);
        if (booking == null) throw new RuntimeException("Booking not found");
        
        booking.setStatus(status);
        bookingBST.update(booking);
        return booking;
    }

    public void deleteBooking(Long id) {
        bookingBST.delete(id);
    }
}