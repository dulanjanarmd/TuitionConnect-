package com.tutor_buddy.model;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReviewBST {
    private Node root;
    private static final String FILE_PATH = "C:\\Users\\damin\\OneDrive\\Documents\\tutor_buddy\\tutor_buddy\\reviews.txt";

    private class Node {
        Review review;
        Node left, right;

        Node(Review review) {
            this.review = review;
        }
    }

    public ReviewBST() {
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

    public void insert(Review review) {
        root = insertRec(root, review);
        saveToFile();
    }

    private Node insertRec(Node root, Review review) {
        if (root == null) return new Node(review);

        if (review.getId() < root.review.getId()) {
            root.left = insertRec(root.left, review);
        } else if (review.getId() > root.review.getId()) {
            root.right = insertRec(root.right, review);
        } else {
            root.review = review; // Update existing
        }

        return root;
    }

    public Review find(Long id) {
        Node node = findRec(root, id);
        return node != null ? node.review : null;
    }

    private Node findRec(Node root, Long id) {
        if (root == null || root.review.getId().equals(id)) return root;

        if (id < root.review.getId()) return findRec(root.left, id);
        else return findRec(root.right, id);
    }

    public void delete(Long id) {
        root = deleteRec(root, id);
        saveToFile();
    }

    private Node deleteRec(Node root, Long id) {
        if (root == null) return null;

        if (id < root.review.getId()) {
            root.left = deleteRec(root.left, id);
        } else if (id > root.review.getId()) {
            root.right = deleteRec(root.right, id);
        } else {
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;

            Review min = minValue(root.right);
            root.review = min;
            root.right = deleteRec(root.right, min.getId());
        }
        return root;
    }

    private Review minValue(Node root) {
        Review min = root.review;
        while (root.left != null) {
            min = root.left.review;
            root = root.left;
        }
        return min;
    }

    public void update(Review review) {
        delete(review.getId());
        insert(review);
    }

    public List<Review> getAllReviews() {
        List<Review> list = new ArrayList<>();
        inOrderTraversal(root, list);
        return list;
    }

    private void inOrderTraversal(Node node, List<Review> list) {
        if (node != null) {
            inOrderTraversal(node.left, list);
            list.add(node.review);
            inOrderTraversal(node.right, list);
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Review review : getAllReviews()) {
                writer.write("BEGIN_REVIEW");
                writer.newLine();
                writer.write("ID:" + review.getId()); writer.newLine();
                writer.write("USER_ID:" + review.getUserId()); writer.newLine();
                writer.write("TUTOR_ID:" + review.getTutorId()); writer.newLine();
                writer.write("TUTOR_NAME:" + review.getTutorName()); writer.newLine();
                writer.write("REVIEW_TEXT:" + review.getReviewText()); writer.newLine();
                writer.write("RATING:" + review.getRating()); writer.newLine();
                writer.write("DATE:" + review.getDate()); writer.newLine();
                writer.write("END_REVIEW");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            Review review = null;
            while ((line = reader.readLine()) != null) {
                if (line.equals("BEGIN_REVIEW")) {
                    review = new Review();
                } else if (line.startsWith("ID:")) {
                    review.setId(Long.parseLong(line.substring(3)));
                } else if (line.startsWith("USER_ID:")) {
                    review.setUserId(Long.parseLong(line.substring(8)));
                } else if (line.startsWith("TUTOR_ID:")) {
                    review.setTutorId(Long.parseLong(line.substring(9)));
                } else if (line.startsWith("TUTOR_NAME:")) {
                    review.setTutorName(line.substring(11));
                } else if (line.startsWith("REVIEW_TEXT:")) {
                    review.setReviewText(line.substring(12));
                } else if (line.startsWith("RATING:")) {
                    review.setRating(Integer.parseInt(line.substring(7)));
                } else if (line.startsWith("DATE:")) {
                    review.setDate(LocalDateTime.parse(line.substring(5)));
                } else if (line.equals("END_REVIEW") && review != null) {
                    insert(review); // Load into BST
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
