package com.wareland.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO request untuk mengupdate review.
 */
public class ReviewUpdateRequest {

    /** Rating baru 1-5 (wajib). */
    @Min(1)
    @Max(5)
    private int rating;

    /** Komentar baru (wajib). */
    @NotBlank
    private String comment;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}