package com.wareland.review.model;

import com.wareland.property.model.Property;
import com.wareland.user.model.Buyer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

/**
 * Entity yang merepresentasikan review dari Buyer untuk Property.
 * Satu Buyer hanya bisa memberikan satu review per Property.
 */
@Entity
@Table(
        name = "reviews",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_review_buyer_property",
                        columnNames = {"buyer_id", "property_id"}
                )
        }
)
public class Review {

    /** ID unik review (auto-generated). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Rating 1-5 (wajib). */
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private int rating;

    /** Komentar review (wajib, maks 1000 karakter). */
    @NotBlank
    @Column(nullable = false, length = 1000)
    private String comment;

    /** Relasi ke Buyer yang membuat review. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "buyer_id", nullable = false)
    private Buyer buyer;

    /** Relasi ke Property yang direview. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    /** Waktu review dibuat (otomatis). */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /** Waktu review diupdate (otomatis). */
    @Column
    private LocalDateTime updatedAt;

    /**
     * Set createdAt otomatis saat pertama kali disimpan.
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Set updatedAt otomatis saat diupdate.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
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

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}