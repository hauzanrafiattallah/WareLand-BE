package com.wareland.review.dto;

import java.time.LocalDateTime;

/**
 * DTO response untuk menampilkan data review.
 */
public class ReviewResponse {

    /** ID unik review. */
    private Long reviewId;

    /** Rating 1-5. */
    private int rating;

    /** Komentar review. */
    private String comment;

    /** Nama buyer yang memberikan review. */
    private String buyerName;

    /** Waktu review dibuat. */
    private LocalDateTime createdAt;

    /**
     * Constructor untuk membuat response.
     */
    public ReviewResponse(Long reviewId, int rating, String comment, String buyerName, LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.buyerName = buyerName;
        this.createdAt = createdAt;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
