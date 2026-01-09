package com.wareland.review.service;

import com.wareland.common.exception.BusinessException;
import com.wareland.common.exception.ResourceNotFoundException;
import com.wareland.property.model.Property;
import com.wareland.property.repository.PropertyRepository;
import com.wareland.review.dto.ReviewBuyerResponse;
import com.wareland.review.dto.ReviewCreateRequest;
import com.wareland.review.dto.ReviewResponse;
import com.wareland.review.dto.ReviewUpdateRequest;
import com.wareland.review.mapper.ReviewMapper;
import com.wareland.review.model.Review;
import com.wareland.review.repository.ReviewRepository;
import com.wareland.user.model.Buyer;
import com.wareland.user.model.User;
import com.wareland.user.model.UserRole;
import com.wareland.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service untuk mengelola logika bisnis Review.
 */
@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final ReviewMapper reviewMapper;

    /**
     * Constructor untuk inject dependency.
     */
    public ReviewService(
            ReviewRepository reviewRepository,
            UserRepository userRepository,
            PropertyRepository propertyRepository,
            ReviewMapper reviewMapper
    ) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
        this.reviewMapper = reviewMapper;
    }

    /**
     * Buat review baru oleh Buyer untuk Property tertentu.
     */
    public ReviewResponse createReview(ReviewCreateRequest request) {

        // Validasi rating
        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new BusinessException("Rating harus antara 1 hingga 5");
        }
        // Validasi comment
        if (request.getComment() == null || request.getComment().isBlank()) {
            throw new BusinessException("Comment tidak boleh kosong");
        }

        // Cari dan validasi buyer
        User user = userRepository.findById(request.getBuyerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Buyer dengan ID " + request.getBuyerId() + " tidak ditemukan")
                );

        if (user.getUserRole() != UserRole.BUYER || !(user instanceof Buyer)) {
            throw new BusinessException("Hanya buyer yang dapat membuat review");
        }

        Buyer buyer = (Buyer) user;

        // Cari property
        Property property = propertyRepository
                .findById(Math.toIntExact(request.getPropertyId()))
                .orElseThrow(() ->
                        new ResourceNotFoundException("Property dengan ID " + request.getPropertyId() + " tidak ditemukan")
                );

        // Cek apakah sudah pernah mereview
        boolean exists = reviewRepository.existsByBuyerUserIdAndPropertyPropertyId(
                buyer.getUserId(), property.getPropertyId()
        );
        if (exists) {
            throw new BusinessException("Anda sudah memberikan review untuk properti ini");
        }

        // Simpan review baru
        Review review = new Review();
        review.setBuyer(buyer);
        review.setProperty(property);
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review saved = reviewRepository.save(review);
        return reviewMapper.toResponse(saved);
    }

    /**
     * Ambil semua review untuk property tertentu.
     */
    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByProperty(Long propertyId) {
        Integer pid = Math.toIntExact(propertyId);
        List<Review> reviews = reviewRepository
                .findAllByPropertyPropertyIdOrderByCreatedAtDesc(pid);

        return reviews.stream()
                .map(reviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Ambil semua review milik buyer tertentu.
     */
    @Transactional(readOnly = true)
    public List<ReviewBuyerResponse> getReviewsByBuyer(Long buyerId) {
        // Validasi: buyer harus ada
        User user = userRepository.findById(buyerId)
                .orElseThrow(() -> new ResourceNotFoundException("Buyer dengan ID " + buyerId + " tidak ditemukan"));
        if (user.getUserRole() != UserRole.BUYER || !(user instanceof Buyer)) {
            throw new ResourceNotFoundException("Buyer dengan ID " + buyerId + " tidak ditemukan");
        }

        List<Review> reviews = reviewRepository.findAllByBuyerUserIdOrderByCreatedAtDesc(buyerId);
        return reviews.stream()
                .map(reviewMapper::toBuyerResponse)
                .collect(Collectors.toList());
    }

    /**
     * Update review yang dimiliki buyer.
     */
    public ReviewResponse updateReview(Long reviewId, Long buyerId, ReviewUpdateRequest request) {

        // Cari review
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review tidak ditemukan"));

        // Validasi ownership
        if (!review.getBuyer().getUserId().equals(buyerId)) {
            throw new BusinessException("Anda tidak berhak mengubah review ini");
        }

        // Validasi input
        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new BusinessException("Rating harus antara 1 hingga 5");
        }
        if (request.getComment() == null || request.getComment().isBlank()) {
            throw new BusinessException("Comment tidak boleh kosong");
        }

        // Update review
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review updated = reviewRepository.save(review);
        return reviewMapper.toResponse(updated);
    }

    /**
     * Hapus review yang dimiliki buyer.
     */
    public void deleteReview(Long reviewId, Long buyerId) {

        // Cari review
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review tidak ditemukan"));

        // Validasi ownership
        if (!review.getBuyer().getUserId().equals(buyerId)) {
            throw new BusinessException("Anda tidak berhak menghapus review ini");
        }

        reviewRepository.delete(review);
    }
}
