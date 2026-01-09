package com.wareland.property.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wareland.review.model.Review;
import com.wareland.user.model.Seller;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Entity yang merepresentasikan data property/gudang.
 */
@Entity
@Table(name = "properties")
public class Property {

    /** ID unik property (auto-generated). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer propertyId;

    /** Alamat lengkap property. */
    @NotBlank
    @Column(nullable = false, length = 255)
    private String address;

    /** Harga sewa property (minimal 0). */
    @Min(0)
    @Column(nullable = false)
    private double price;

    /** URL gambar property (opsional). */
    @Column(length = 255)
    private String imageUrl;

    /** Deskripsi detail property (opsional). */
    @Column(length = 2000)
    private String description;

    /** Relasi ke Seller pemilik property. */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    @JsonIgnoreProperties("properties")
    private Seller seller;

    /** Daftar review yang diberikan pada property ini. */
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("property")
    private List<Review> reviews = new ArrayList<>();

    /**
     * Update detail property tanpa mengubah ownership.
     */
    public void updateDetails(String newAddress, double newPrice, String newDescription, String newImageUrl) {
        if (newAddress != null && !newAddress.isBlank()) {
            this.address = newAddress;
        }
        if (newPrice >= 0) {
            this.price = newPrice;
        }
        if (newDescription != null) {
            this.description = newDescription;
        }
        if (newImageUrl != null && !newImageUrl.isBlank()) {
            this.imageUrl = newImageUrl;
        }
    }

    /**
     * Tampilkan ringkasan property dalam format string.
     */
    public String displayProperty() {
        return String.format("Property{id=%d, address='%s', price=%.2f, image='%s'}", propertyId, address, price, imageUrl);
    }

    // ========================
    // Getter & Setter
    // ========================

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
