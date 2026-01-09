package com.wareland.property.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wareland.common.response.ApiResponse;
import com.wareland.property.model.Property;
import com.wareland.property.service.PropertyService;
import com.wareland.user.model.Seller;
import com.wareland.user.model.User;
import com.wareland.user.model.UserRole;
import com.wareland.user.repository.UserRepository;

/**
 * Controller untuk menangani CRUD property milik Seller.
 */
@RestController
@RequestMapping("/api/seller/properties")
public class PropertyController {

    private final PropertyService propertyService;
    private final UserRepository userRepository;

    /**
     * Constructor untuk inject dependency.
     */
    public PropertyController(PropertyService propertyService,
                              UserRepository userRepository) {
        this.propertyService = propertyService;
        this.userRepository = userRepository;
    }

    /**
     * Endpoint untuk membuat property baru.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Property>> create(@RequestBody @Validated Property request) {
        Seller seller = getCurrentSeller();
        Property created = propertyService.createProperty(seller, request);
        return ResponseEntity.ok(ApiResponse.success("Property berhasil dibuat", created));
    }

    /**
     * Endpoint untuk mengambil semua property milik Seller yang login.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Property>>> listOwn() {
        Seller seller = getCurrentSeller();
        List<Property> properties = propertyService.getSellerProperties(seller);
        return ResponseEntity.ok(ApiResponse.success(properties));
    }

    /**
     * Endpoint untuk mengupdate property berdasarkan ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable("id") int id,
                                                    @RequestBody @Validated Property request) {
        Seller seller = getCurrentSeller();
        request.setPropertyId(id); // gunakan ID dari path, bukan body
        propertyService.updateProperty(seller, request);
        return ResponseEntity.ok(ApiResponse.success("Property berhasil diperbarui", null));
    }

    /**
     * Endpoint untuk menghapus property berdasarkan ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") int id) {
        Seller seller = getCurrentSeller();
        propertyService.deleteProperty(seller, id);
        return ResponseEntity.ok(ApiResponse.success("Property berhasil dihapus", null));
    }

    /**
     * Ambil Seller dari SecurityContext dan validasi role.
     */
    private Seller getCurrentSeller() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (auth != null) ? auth.getName() : null;
        if (username == null) {
            throw new com.wareland.common.exception.InvalidCredentialException("Tidak terautentikasi");
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new com.wareland.common.exception.ResourceNotFoundException("User tidak ditemukan"));

        if (user.getUserRole() != UserRole.SELLER) {
            throw new com.wareland.common.exception.BusinessException("Hanya Seller yang boleh mengakses fitur ini");
        }
        return (Seller) user;
    }
}
