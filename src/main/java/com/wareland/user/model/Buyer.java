package com.wareland.user.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BUYER")
public class Buyer extends User {

    @Override
    public void register() {
        // Implementasi khusus jika diperlukan untuk Buyer
        // Misalnya inisialisasi default tertentu
        setUserRole(UserRole.BUYER);
    }
}
