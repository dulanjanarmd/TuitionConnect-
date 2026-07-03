package com.tutor_buddy.model;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BookingBST {
    private Node root;
    private static final String FILE_PATH = "C:\\Users\\damin\\OneDrive\\Documents\\tutor_buddy\\tutor_buddy\\bookings.txt";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private class Node {
        Booking booking;
        Node left;
        Node right;

        Node(Booking booking) {
            this.booking = booking;
            this.left = null;
            this.right = null;
        }
    }

    public BookingBST() {
        this.root = null;
        File file = new File(FILE_PATH);
        File directory = file.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        loadFromFile(); 
    }

    public Long generateNewId() {
        Long maxId = getMaxId(root);
        return maxId != null ? maxId + 1 : 1L;
    }

    private Long getMaxId(Node node) {
        if (node == null) return null;
        if (node.right == null) return node.booking.getId();
        return getMaxId(node.right);
    }

    // Insert a booking into the BST
    public void insert(Booking booking) {
        if (booking.getId() == null) {
            booking.setId(generateNewId());
        }
        
        if (booking.getStatus() == null || booking.getStatus().isEmpty()) {
            booking.setStatus("pending");
        }
        
        root = insertRec(root, booking);
        saveToFile(); // Save to file after insertion
    }

    private Node insertRec(Node root, Booking booking) {
        if (root == null) {
            root = new Node(booking);
            return root;
        }

        if (booking.getId() < root.booking.getId()) {
            root.left = insertRec(root.left, booking);
        } else if (booking.getId() > root.booking.getId()) {
            root.right = insertRec(root.right, booking);
        } else {
            root.booking = booking;
        }

        return root;
    }

    // Find a booking by ID
    public Booking find(Long id) {
        Node node = findRec(root, id);
        return node != null ? node.booking : null;
    }

    private Node findRec(Node root, Long id) {
        if (root == null || root.booking.getId().equals(id)) {
            return root;
        }

        if (id < root.booking.getId()) {
            return findRec(root.left, id);
        }

        return findRec(root.right, id);
    }

    // Delete a booking by ID
    public void delete(Long id) {
        root = deleteRec(root, id);
        saveToFile(); 
    }

    private Node deleteRec(Node root, Long id) {
        if (root == null) {
            return root;
        }

        if (id < root.booking.getId()) {
            root.left = deleteRec(root.left, id);
        } else if (id > root.booking.getId()) {
            root.right = deleteRec(root.right, id);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            Booking minBooking = minValue(root.right);
            root.booking.setId(minBooking.getId());
            root.booking.setUserId(minBooking.getUserId());
            root.booking.setTutorId(minBooking.getTutorId());
            root.booking.setSubject(minBooking.getSubject());
            root.booking.setSessionDate(minBooking.getSessionDate());
            root.booking.setStartTime(minBooking.getStartTime());
            root.booking.setEndTime(minBooking.getEndTime());
            root.booking.setDuration(minBooking.getDuration());
            root.booking.setPaymentMethod(minBooking.getPaymentMethod());
            root.booking.setTotal(minBooking.getTotal());
            root.booking.setStatus(minBooking.getStatus());
            root.booking.setSpecialRequests(minBooking.getSpecialRequests());

            root.right = deleteRec(root.right, minBooking.getId());
        }

        return root;
    }

    private Booking minValue(Node root) {
        Booking minv = root.booking;
        while (root.left != null) {
            minv = root.left.booking;
            root = root.left;
        }
        return minv;
    }

    // Update a booking
    public void update(Booking booking) {
        delete(booking.getId());
        insert(booking);
    }

    // Update booking status
    public boolean updateStatus(Long id, String status) {
        Booking booking = find(id);
        if (booking != null) {
            booking.setStatus(status);
            update(booking);
            return true;
        }
        return false;
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        inOrderTraversal(root, bookings);
        return bookings;
    }

    private void inOrderTraversal(Node node, List<Booking> bookings) {
        if (node != null) {
            inOrderTraversal(node.left, bookings);
            bookings.add(node.booking);
            inOrderTraversal(node.right, bookings);
        }
    }

    // Find bookings by user ID
    public List<Booking> findByUser(Long userId) {
        List<Booking> result = new ArrayList<>();
        findByUserRec(root, result, userId);
        return result;
    }

    private void findByUserRec(Node node, List<Booking> list, Long userId) {
        if (node != null) {
            findByUserRec(node.left, list, userId);
            if (userId.equals(node.booking.getUserId())) {
                list.add(node.booking);
            }
            findByUserRec(node.right, list, userId);
        }
    }

    // Find bookings by user ID and status
    public List<Booking> findByUserAndStatus(Long userId, String status) {
        List<Booking> result = new ArrayList<>();
        findByUserAndStatusRec(root, result, userId, status);
        return result;
    }

    private void findByUserAndStatusRec(Node node, List<Booking> list, Long userId, String status) {
        if (node != null) {
            findByUserAndStatusRec(node.left, list, userId, status);
            if (userId.equals(node.booking.getUserId()) && 
                (status == null || status.equalsIgnoreCase(node.booking.getStatus()))) {
                list.add(node.booking);
            }
            findByUserAndStatusRec(node.right, list, userId, status);
        }
    }

    // Find bookings by tutor ID
    public List<Booking> findByTutor(Long tutorId) {
        List<Booking> result = new ArrayList<>();
        findByTutorRec(root, result, tutorId);
        return result;
    }

    private void findByTutorRec(Node node, List<Booking> list, Long tutorId) {
        if (node != null) {
            findByTutorRec(node.left, list, tutorId);
            if (tutorId.equals(node.booking.getTutorId())) {
                list.add(node.booking);
            }
            findByTutorRec(node.right, list, tutorId);
        }
    }

    // Find upcoming bookings
    public List<Booking> findUpcomingBookings() {
        List<Booking> allBookings = getAllBookings();
        List<Booking> upcomingBookings = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        
        for (Booking booking : allBookings) {
            try {
                // LocalDateTime bookingTime = LocalDateTime.parse(booking.getSessionDate(), DATE_FORMATTER);
                // if (bookingTime.isAfter(now) && 
                //     !booking.getStatus().equalsIgnoreCase("cancelled") && 
                //     !booking.getStatus().equalsIgnoreCase("completed")) {
                //     upcomingBookings.add(booking);
                // }
            } catch (Exception e) {
                // Skip bookings with invalid date format
            }
        }
        
        // Sort by session date
        // upcomingBookings.sort(Comparator.comparing(b -> {
        //     try {
        //         return LocalDateTime.parse(b.getSessionDate(), DATE_FORMATTER);
        //     } catch (Exception e) {
        //         return LocalDateTime.MAX;
        //     }
        // }));
        
        return upcomingBookings;
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            List<Booking> bookings = getAllBookings();
            for (Booking booking : bookings) {
                writer.write("BEGIN_BOOKING");
                writer.newLine();
                writer.write("ID:" + booking.getId());
                writer.newLine();
                writer.write("USER_ID:" + booking.getUserId());
                writer.newLine();
                writer.write("TUTOR_ID:" + booking.getTutorId());
                writer.newLine();
                writer.write("SUBJECT:" + (booking.getSubject() != null ? booking.getSubject() : ""));
                writer.newLine();
                writer.write("SESSION_DATE:" + (booking.getSessionDate() != null ? booking.getSessionDate() : ""));
                writer.newLine();
                writer.write("START_TIME:" + (booking.getStartTime() != null ? booking.getStartTime() : ""));
                writer.newLine();
                writer.write("END_TIME:" + (booking.getEndTime() != null ? booking.getEndTime() : ""));
                writer.newLine();
                writer.write("DURATION:" + booking.getDuration());
                writer.newLine();
                writer.write("PAYMENT_METHOD:" + (booking.getPaymentMethod() != null ? booking.getPaymentMethod() : ""));
                writer.newLine();
                writer.write("TOTAL:" + booking.getTotal());
                writer.newLine();
                writer.write("STATUS:" + (booking.getStatus() != null ? booking.getStatus() : ""));
                writer.newLine();
                writer.write("SPECIAL_REQUESTS:" + (booking.getSpecialRequests() != null ? booking.getSpecialRequests() : ""));
                writer.newLine();
                writer.write("END_BOOKING");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load bookings from file
    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            Booking currentBooking = null;
            
            while ((line = reader.readLine()) != null) {
                if (line.equals("BEGIN_BOOKING")) {
                    currentBooking = new Booking();
                } else if (line.equals("END_BOOKING") && currentBooking != null) {
                    root = insertRec(root, currentBooking);
                    currentBooking = null;
                } else if (currentBooking != null && line.contains(":")) {
                    String[] parts = line.split(":", 2);
                    String key = parts[0];
                    String value = parts.length > 1 ? parts[1] : "";
                    
                    switch (key) {
                        case "ID":
                            currentBooking.setId(Long.parseLong(value));
                            break;
                        case "USER_ID":
                            currentBooking.setUserId(Long.parseLong(value));
                            break;
                        case "TUTOR_ID":
                            currentBooking.setTutorId(Long.parseLong(value));
                            break;
                        case "SUBJECT":
                            currentBooking.setSubject(value);
                            break;
                        // case "SESSION_DATE":
                        //     currentBooking.setSessionDate(value);
                        //     break;
                        case "START_TIME":
                            currentBooking.setStartTime(value);
                            break;
                        case "END_TIME":
                            currentBooking.setEndTime(value);
                            break;
                        case "DURATION":
                            currentBooking.setDuration(Double.parseDouble(value));
                            break;
                        case "PAYMENT_METHOD":
                            currentBooking.setPaymentMethod(value);
                            break;
                        case "TOTAL":
                            currentBooking.setTotal(Double.parseDouble(value));
                            break;
                        case "STATUS":
                            currentBooking.setStatus(value);
                            break;
                        case "SPECIAL_REQUESTS":
                            currentBooking.setSpecialRequests(value);
                            break;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Backup the file
    public boolean backupFile() {
        try {
            String backupFileName = FILE_PATH + ".backup." + System.currentTimeMillis();
            File sourceFile = new File(FILE_PATH);
            File destFile = new File(backupFileName);
            
            try (FileInputStream fis = new FileInputStream(sourceFile);
                 FileOutputStream fos = new FileOutputStream(destFile)) {
                
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
            }
            
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get count of bookings
    public int getBookingCount() {
        return getAllBookings().size();
    }
}