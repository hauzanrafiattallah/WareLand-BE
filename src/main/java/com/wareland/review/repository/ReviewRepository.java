package com.wareland.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wareland.review.model.Review;

/**
 * Repository untuk akses data Review ke database.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /** Cek apakah buyer sudah pernah mereview property tertentu. */
    boolean existsByBuyerUserIdAndPropertyPropertyId(Long buyerId, Integer propertyId);

    /** Ambil semua review berdasarkan property ID, diurutkan dari terbaru. */
    List<Review> findAllByPropertyPropertyIdOrderByCreatedAtDesc(Integer propertyId);

    /** Ambil semua review milik buyer tertentu, diurutkan dari terbaru. */
    List<Review> findAllByBuyerUserIdOrderByCreatedAtDesc(Long buyerId);
}
