package com.wareland.catalog.controller;

import com.wareland.catalog.dto.CatalogPropertyResponse;
import com.wareland.catalog.dto.CatalogSearchRequest;
import com.wareland.catalog.service.CatalogService;
import com.wareland.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller untuk menampilkan katalog property ke publik.
 */
@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    /**
     * Constructor untuk inject dependency.
     */
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    /**
     * Endpoint untuk mengambil semua property.
     */
    @GetMapping("/properties")
    public ResponseEntity<ApiResponse<List<CatalogPropertyResponse>>> getAllProperties() {
        List<CatalogPropertyResponse> data = catalogService.showAllProperties();
        if (data.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success("Properti tidak tersedia", data));
        }
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    /**
     * Endpoint untuk mencari property berdasarkan keyword dan range harga.
     */
    @GetMapping("/properties/search")
    public ResponseEntity<ApiResponse<List<CatalogPropertyResponse>>> searchProperties(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice
    ) {
        // Buat objek request dari parameter
        CatalogSearchRequest req = new CatalogSearchRequest();
        req.setKeyword(keyword);
        req.setMinPrice(minPrice);
        req.setMaxPrice(maxPrice);

        List<CatalogPropertyResponse> data = catalogService.searchProperties(req);
        if (data.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success("Properti tidak tersedia", data));
        }
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    /**
     * Endpoint untuk mengambil detail property berdasarkan ID.
     */
    @GetMapping("/properties/{propertyId}")
    public ResponseEntity<ApiResponse<CatalogPropertyResponse>> getPropertyDetail(@PathVariable int propertyId) {
        CatalogPropertyResponse detail = catalogService.getPropertyDetail(propertyId);
        if (detail == null) {
            return ResponseEntity.ok(ApiResponse.success("Properti tidak tersedia", null));
        }
        return ResponseEntity.ok(ApiResponse.success(detail));
    }
}
