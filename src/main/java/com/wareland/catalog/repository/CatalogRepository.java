package com.wareland.catalog.repository;

import com.wareland.property.model.Property;
import com.wareland.property.repository.PropertyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Adapter repository untuk membaca data Property sebagai katalog publik.
 * BUKAN Spring Data JPA repository langsung.
 */
@Repository
public class CatalogRepository {

    private final PropertyRepository propertyRepository;

    @PersistenceContext
    private EntityManager em;

    /**
     * Constructor untuk inject repository.
     */
    public CatalogRepository(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    /**
     * Ambil semua property.
     */
    public List<Property> findAll() {
        return propertyRepository.findAll();
    }

    /**
     * Cari property berdasarkan ID.
     */
    public Optional<Property> findById(int propertyId) {
        return propertyRepository.findById(propertyId);
    }

    /**
     * Cari property berdasarkan keyword pada alamat/deskripsi.
     */
    public List<Property> searchByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return new ArrayList<>();
        }
        String like = "%" + keyword.trim().toLowerCase() + "%";
        TypedQuery<Property> q = em.createQuery(
                "SELECT p FROM Property p WHERE LOWER(p.address) LIKE :kw OR LOWER(p.description) LIKE :kw",
                Property.class
        );
        q.setParameter("kw", like);
        return q.getResultList();
    }

    /**
     * Filter property berdasarkan keyword dan range harga.
     */
    public List<Property> filterByCriteria(String keyword, Double minPrice, Double maxPrice) {
        StringBuilder jpql = new StringBuilder("SELECT p FROM Property p WHERE 1=1");
        boolean useKeyword = keyword != null && !keyword.isBlank();
        if (useKeyword) {
            jpql.append(" AND (LOWER(p.address) LIKE :kw OR LOWER(p.description) LIKE :kw)");
        }
        if (minPrice != null) {
            jpql.append(" AND p.price >= :minPrice");
        }
        if (maxPrice != null) {
            jpql.append(" AND p.price <= :maxPrice");
        }

        TypedQuery<Property> q = em.createQuery(jpql.toString(), Property.class);
        if (useKeyword) {
            q.setParameter("kw", "%" + keyword.trim().toLowerCase() + "%");
        }
        if (minPrice != null) {
            q.setParameter("minPrice", minPrice);
        }
        if (maxPrice != null) {
            q.setParameter("maxPrice", maxPrice);
        }
        return q.getResultList();
    }
}
