package com.tutor_buddy.controller;

import com.tutor_buddy.model.Booking;
import com.tutor_buddy.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*") 
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

  // Get all bookings
    @GetMapping("/all")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping
    public List<Booking> getBookingsByUser(@RequestParam Long userId,
                                         @RequestParam(required = false) String status) {
        if (status != null) {
            return bookingService.getBookingsByUserAndStatus(userId, status);
        }
        return bookingService.getBookingsByUser(userId);
    }

    // Get bookings by tutor
    @GetMapping("/tutor/{tutorId}")
    public List<Booking> getBookingsByTutor(@PathVariable Long tutorId) {
        return bookingService.getBookingsByTutor(tutorId);
    }

    // Get upcoming bookings
    @GetMapping("/upcoming")
    public List<Booking> getUpcomingBookings() {
        return bookingService.getUpcomingBookings();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @PutMapping("/{id}")
    public Booking updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        return bookingService.updateBooking(id, booking);
    }

    // Update booking status
    @PutMapping("/{id}/status")
    public Booking updateBookingStatus(@PathVariable Long id, @RequestParam String status) {
        return bookingService.updateBookingStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "Booking deleted successfully";
    }
}