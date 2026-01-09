package com.wareland.property.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wareland.common.exception.BadRequestException;
import com.wareland.common.exception.BusinessException;
import com.wareland.property.model.Property;
import com.wareland.property.repository.PropertyRepository;
import com.wareland.user.model.Seller;
import com.wareland.user.model.UserRole;

/**
 * Service untuk mengelola logika bisnis Property.
 */
@Service
@Transactional
public class PropertyService {

    private final PropertyRepository propertyRepository;

    /**
     * Constructor untuk inject repository.
     */
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    /**
     * Buat property baru untuk Seller tertentu.
     */
    public Property createProperty(Seller seller, Property data) {
        validateSeller(seller);
        if (data == null) {
            throw new BadRequestException("Data property tidak boleh kosong");
        }
        data.setSeller(seller); // set ownership
        return propertyRepository.save(data);
    }

    /**
     * Update detail property yang dimiliki Seller.
     */
    public void updateProperty(Seller seller, Property property) {
        validateSeller(seller);
        if (property == null || property.getPropertyId() == null) {
            throw new BadRequestException("Property ID wajib diisi untuk update");
        }

        Property existing = propertyRepository.findByPropertyIdAndSeller(property.getPropertyId(), seller)
                .orElseThrow(() -> new BusinessException("Anda tidak berhak mengubah property ini atau property tidak ditemukan"));

        existing.updateDetails(property.getAddress(), property.getPrice(), property.getDescription(), property.getImageUrl());
        propertyRepository.save(existing);
    }

    /**
     * Hapus property yang dimiliki Seller.
     */
    public void deleteProperty(Seller seller, int propertyId) {
        validateSeller(seller);
        Property existing = propertyRepository.findByPropertyIdAndSeller(propertyId, seller)
                .orElseThrow(() -> new BusinessException("Anda tidak berhak menghapus property ini atau property tidak ditemukan"));
        propertyRepository.delete(existing);
    }

    /**
     * Verifikasi apakah Seller memiliki property tertentu.
     */
    @Transactional(readOnly = true)
    public boolean verifyOwnership(Seller seller, int propertyId) {
        validateSeller(seller);
        return propertyRepository.existsByPropertyIdAndSeller(propertyId, seller);
    }

    /**
     * Ambil semua property milik Seller tertentu.
     */
    @Transactional(readOnly = true)
    public List<Property> getSellerProperties(Seller seller) {
        validateSeller(seller);
        return propertyRepository.findBySeller(seller);
    }

    /**
     * Validasi bahwa user adalah Seller yang valid.
     */
    private void validateSeller(Seller seller) {
        if (seller == null || seller.getUserRole() != UserRole.SELLER) {
            throw new BusinessException("Hanya Seller yang dapat mengelola Property");
        }
    }
}
