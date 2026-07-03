package com.tutor_buddy.model;

public class BSTNode {
    private User tutor;
    private BSTNode left;
    private BSTNode right;

    public BSTNode(User tutor) {
        this.tutor = tutor;
        this.left = null;
        this.right = null;
    }

    public User getTutor() {
        return tutor;
    }

    public void setTutor(User tutor) {
        this.tutor = tutor;
    }

    public BSTNode getLeft() {
        return left;
    }

    public void setLeft(BSTNode left) {
        this.left = left;
    }

    public BSTNode getRight() {
        return right;
    }

    public void setRight(BSTNode right) {
        this.right = right;
    }
}