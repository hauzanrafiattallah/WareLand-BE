package com.wareland.review.dto;

import java.time.LocalDateTime;

/**
 * DTO response untuk review milik buyer tertentu.
 */
public class ReviewBuyerResponse {

    /** ID unik review. */
    private Long reviewId;

    /** ID property yang direview. */
    private Long propertyId;

    /** Judul/alamat property. */
    private String propertyTitle;

    /** Rating 1-5. */
    private int rating;

    /** Komentar review. */
    private String comment;

    /** Waktu review dibuat. */
    private LocalDateTime createdAt;

    /**
     * Constructor untuk membuat response.
     */
    public ReviewBuyerResponse(Long reviewId, Long propertyId, String propertyTitle, int rating, String comment, LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.propertyId = propertyId;
        this.propertyTitle = propertyTitle;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public String getPropertyTitle() {
        return propertyTitle;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
