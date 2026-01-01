package com.wareland.catalog.dto;

import java.time.LocalDateTime;

/**
 * DTO read-only untuk katalog properti publik.
 */
public class CatalogPropertyResponse {

    private int propertyId;
    private String address;
    private double price;
    private String description;
    private SellerInfo seller;

    public CatalogPropertyResponse() {
    }

    public CatalogPropertyResponse(int propertyId, String address, double price, String description, SellerInfo seller) {
        this.propertyId = propertyId;
        this.address = address;
        this.price = price;
        this.description = description;
        this.seller = seller;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SellerInfo getSeller() {
        return seller;
    }

    public void setSeller(SellerInfo seller) {
        this.seller = seller;
    }

    /**
     * Inner class untuk informasi seller.
     */
    public static class SellerInfo {
        private Long userId;
        private String username;
        private String password;
        private String name;
        private String email;
        private String phoneNumber;
        private String userRole;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public SellerInfo() {
        }

        public SellerInfo(Long userId, String username, String password, String name, String email,
                          String phoneNumber, String userRole, LocalDateTime createdAt, LocalDateTime updatedAt) {
            this.userId = userId;
            this.username = username;
            this.password = password;
            this.name = name;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.userRole = userRole;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getUserRole() {
            return userRole;
        }

        public void setUserRole(String userRole) {
            this.userRole = userRole;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public LocalDateTime getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
