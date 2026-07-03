package com.tutor_buddy.Repository;



import com.tutor_buddy.model.Tutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

    @Repository
    public class TutorRepository {
        private Node root;
        private long sequence = 1;

        // BST Node class
        private static class Node {
            Long id;
            Tutor tutor;
            Node left;
            Node right;

            Node(Long id, Tutor tutor) {
                this.id = id;
                this.tutor = tutor;
                this.left = null;
                this.right = null;
            }
        }

        // Insert a tutor into the BST
        public Tutor save(Tutor tutor) {
            if (tutor.getId() == null) {
                tutor.setId(sequence++);
            }
            root = insert(root, tutor.getId(), tutor);
            return tutor;
        }

        private Node insert(Node node, Long id, Tutor tutor) {
            if (node == null) {
                return new Node(id, tutor);
            }
            if (id < node.id) {
                node.left = insert(node.left, id, tutor);
            } else if (id > node.id) {
                node.right = insert(node.right, id, tutor);
            } else {
                node.tutor = tutor; // Update existing tutor
            }
            return node;
        }

        // Find a tutor by ID
        public Tutor findById(Long id) {
            Node node = find(root, id);
            return node != null ? node.tutor : null;
        }

        private Node find(Node node, Long id) {
            if (node == null || node.id.equals(id)) {
                return node;
            }
            if (id < node.id) {
                return find(node.left, id);
            }
            return find(node.right, id);
        }

        // Get all tutors (in-order traversal)
        public List<Tutor> findAll() {
            List<Tutor> tutors = new ArrayList<>();
            inOrderTraversal(root, tutors);
            return tutors;
        }

        private void inOrderTraversal(Node node, List<Tutor> tutors) {
            if (node != null) {
                inOrderTraversal(node.left, tutors);
                tutors.add(node.tutor);
                inOrderTraversal(node.right, tutors);
            }
        }

        // Delete a tutor by ID
        public void delete(Long id) {
            root = delete(root, id);
        }

        private Node delete(Node node, Long id) {
            if (node == null) {
                return null;
            }
            if (id < node.id) {
                node.left = delete(node.left, id);
            } else if (id > node.id) {
                node.right = delete(node.right, id);
            } else {
                // Node with only one child or no child
                if (node.left == null) {
                    return node.right;
                } else if (node.right == null) {
                    return node.left;
                }
                // Node with two children: Get the inorder successor (smallest in the right subtree)
                Node temp = minValueNode(node.right);
                node.id = temp.id;
                node.tutor = temp.tutor;
                node.right = delete(node.right, temp.id);
            }
            return node;
        }

        private Node minValueNode(Node node) {
            Node current = node;
            while (current.left != null) {
                current = current.left;
            }
            return current;
        }

        // Find tutors by subject
        public List<Tutor> findBySubject(String subject) {
            return findAll().stream()
                    .filter(tutor -> tutor.getSubjects() != null && tutor.getSubjects().contains(subject))
                    .collect(Collectors.toList());
        }

        // Find tutors by status
        public List<Tutor> findByStatus(String status) {
            return findAll().stream()
                    .filter(tutor -> tutor.getStatus() != null && tutor.getStatus().equals(status))
                    .collect(Collectors.toList());
        }
    }

