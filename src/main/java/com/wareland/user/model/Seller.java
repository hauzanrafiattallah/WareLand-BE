package com.wareland.user.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SELLER")
public class Seller extends User {

    @Override
    public void register() {
        // Implementasi khusus jika diperlukan untuk Seller
        setUserRole(UserRole.SELLER);
    }
}
