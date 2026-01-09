package com.wareland.review.dto;

import jakarta.validation.constraints.*;

/**
 * DTO request untuk membuat review baru.
 */
public class ReviewCreateRequest {

    /** ID buyer yang membuat review (wajib). */
    @NotNull
    private Long buyerId;

    /** ID property yang direview (wajib). */
    @NotNull
    private Long propertyId;

    /** Rating 1-5 (wajib). */
    @Min(1)
    @Max(5)
    private int rating;

    /** Komentar review (wajib). */
    @NotBlank
    private String comment;

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

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
