-- =============================================
-- V2: Create All Tables for WareLand Database
-- =============================================

-- 1. Tabel Users (Single Table Inheritance untuk Buyer dan Seller)
CREATE TABLE IF NOT EXISTS users (
    user_id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20) NOT NULL,
    image_url VARCHAR(500),
    role VARCHAR(20) NOT NULL,  -- Discriminator: 'BUYER' atau 'SELLER'
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 2. Tabel Properties (Dimiliki oleh Seller)
CREATE TABLE IF NOT EXISTS properties (
    property_id SERIAL PRIMARY KEY,
    address VARCHAR(255) NOT NULL,
    price DOUBLE PRECISION NOT NULL CHECK (price >= 0),
    description VARCHAR(2000),
    image_url VARCHAR(255),
    seller_id BIGINT NOT NULL,
    CONSTRAINT fk_property_seller FOREIGN KEY (seller_id) 
        REFERENCES users(user_id) ON DELETE CASCADE
);

-- 3. Tabel Reviews (Diberikan oleh Buyer untuk Property)
CREATE TABLE IF NOT EXISTS reviews (
    id BIGSERIAL PRIMARY KEY,
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment VARCHAR(1000) NOT NULL,
    buyer_id BIGINT NOT NULL,
    property_id INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_review_buyer FOREIGN KEY (buyer_id) 
        REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_review_property FOREIGN KEY (property_id) 
        REFERENCES properties(property_id) ON DELETE CASCADE,
    CONSTRAINT uk_review_buyer_property UNIQUE (buyer_id, property_id)
);

-- 4. Tabel Revoked Tokens (untuk logout/invalidasi JWT)
CREATE TABLE IF NOT EXISTS revoked_tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(512) NOT NULL UNIQUE,
    revoked_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Index untuk performa
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_properties_seller ON properties(seller_id);
CREATE INDEX IF NOT EXISTS idx_reviews_property ON reviews(property_id);
CREATE INDEX IF NOT EXISTS idx_reviews_buyer ON reviews(buyer_id);
CREATE INDEX IF NOT EXISTS idx_revoked_token_token ON revoked_tokens(token);
