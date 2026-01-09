package com.wareland.property.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wareland.property.model.Property;
import com.wareland.user.model.Seller;

/**
 * Repository untuk akses data Property ke database.
 */
@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

    /** Cari semua property milik Seller tertentu. */
    List<Property> findBySeller(Seller seller);

    /** Cek apakah property dengan ID tertentu dimiliki oleh Seller. */
    boolean existsByPropertyIdAndSeller(Integer propertyId, Seller seller);

    /** Cari property berdasarkan ID dan Seller pemiliknya. */
    Optional<Property> findByPropertyIdAndSeller(Integer propertyId, Seller seller);
}
