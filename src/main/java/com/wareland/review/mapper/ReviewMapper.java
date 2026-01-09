package com.wareland.review.mapper;

import com.wareland.review.dto.ReviewBuyerResponse;
import com.wareland.review.dto.ReviewResponse;
import com.wareland.review.model.Review;
import org.springframework.stereotype.Component;

/**
 * Mapper untuk konversi entity Review ke DTO response.
 */
@Component
public class ReviewMapper {

    /**
     * Konversi Review ke ReviewResponse (untuk tampilan publik).
     */
    public ReviewResponse toResponse(Review review) {
        String buyerName = review.getBuyer() != null ? review.getBuyer().getName() : null;
        return new ReviewResponse(
                review.getId(),
                review.getRating(),
                review.getComment(),
                buyerName,
                review.getCreatedAt()
        );
    }

    /**
     * Konversi Review ke ReviewBuyerResponse (untuk tampilan buyer).
     */
    public ReviewBuyerResponse toBuyerResponse(Review review) {
        Long propertyId = review.getProperty() != null && review.getProperty().getPropertyId() != null
                ? review.getProperty().getPropertyId().longValue() : null;
        // Gunakan alamat sebagai judul (field title belum ada di Property)
        String propertyTitle = review.getProperty() != null ? review.getProperty().getAddress() : null;
        return new ReviewBuyerResponse(
                review.getId(),
                propertyId,
                propertyTitle,
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        );
    }
}
