package com.wareland.catalog.service;

import com.wareland.catalog.dto.CatalogPropertyResponse;
import com.wareland.catalog.dto.CatalogSearchRequest;
import com.wareland.catalog.mapper.CatalogMapper;
import com.wareland.catalog.repository.CatalogRepository;
import com.wareland.property.model.Property;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service untuk mengelola logika bisnis katalog property publik.
 */
@Service
public class CatalogService {

    private final CatalogRepository catalogRepository;
    private final CatalogMapper catalogMapper;

    /**
     * Constructor untuk inject repository dan mapper.
     */
    public CatalogService(CatalogRepository catalogRepository, CatalogMapper catalogMapper) {
        this.catalogRepository = Objects.requireNonNull(catalogRepository);
        this.catalogMapper = Objects.requireNonNull(catalogMapper);
    }

    /**
     * Tampilkan semua property dalam katalog.
     */
    public List<CatalogPropertyResponse> showAllProperties() {
        List<Property> props = catalogRepository.findAll();
        return props.stream().map(catalogMapper::toResponse).collect(Collectors.toList());
    }

    /**
     * Cari property berdasarkan kriteria pencarian.
     */
    public List<CatalogPropertyResponse> searchProperties(CatalogSearchRequest request) {
        List<Property> props = catalogRepository.filterByCriteria(
                request != null ? request.getKeyword() : null,
                request != null ? request.getMinPrice() : null,
                request != null ? request.getMaxPrice() : null
        );
        return props.stream().map(catalogMapper::toResponse).collect(Collectors.toList());
    }

    /**
     * Ambil detail property berdasarkan ID.
     */
    public CatalogPropertyResponse getPropertyDetail(int propertyId) {
        return catalogRepository.findById(propertyId)
                .map(catalogMapper::toResponse)
                .orElse(null); // katalog publik: jika kosong, bukan error
    }
}
