-- =============================================
-- V3: Insert Dummy Data for WareLand Database
-- =============================================

-- Password dummy: "Password123!" di-hash dengan BCrypt
-- Hash ini adalah hasil encode dari "Password123!"
-- Dalam production, user akan register sendiri dengan password ter-hash

-- =============================================
-- 1. Dummy Sellers (3 sellers)
-- =============================================
INSERT INTO users (username, password, name, email, phone_number, role, created_at, updated_at)
VALUES 
    ('seller1', '$2a$10$N9qo8uLOickgx2ZMRZoMys6fS9QzYr0xhz4y/zHmK.3nK4xPqOVG2', 
     'Budi Santoso', 'budi.santoso@email.com', '081234567890', 'SELLER', NOW(), NOW()),
    ('seller2', '$2a$10$N9qo8uLOickgx2ZMRZoMys6fS9QzYr0xhz4y/zHmK.3nK4xPqOVG2', 
     'Dewi Lestari', 'dewi.lestari@email.com', '081234567891', 'SELLER', NOW(), NOW()),
    ('seller3', '$2a$10$N9qo8uLOickgx2ZMRZoMys6fS9QzYr0xhz4y/zHmK.3nK4xPqOVG2', 
     'Agus Wijaya', 'agus.wijaya@email.com', '081234567892', 'SELLER', NOW(), NOW())
ON CONFLICT (username) DO NOTHING;

-- =============================================
-- 2. Dummy Buyers (3 buyers)
-- =============================================
INSERT INTO users (username, password, name, email, phone_number, role, created_at, updated_at)
VALUES 
    ('buyer1', '$2a$10$N9qo8uLOickgx2ZMRZoMys6fS9QzYr0xhz4y/zHmK.3nK4xPqOVG2', 
     'Rina Maharani', 'rina.maharani@email.com', '082345678901', 'BUYER', NOW(), NOW()),
    ('buyer2', '$2a$10$N9qo8uLOickgx2ZMRZoMys6fS9QzYr0xhz4y/zHmK.3nK4xPqOVG2', 
     'Joko Prasetyo', 'joko.prasetyo@email.com', '082345678902', 'BUYER', NOW(), NOW()),
    ('buyer3', '$2a$10$N9qo8uLOickgx2ZMRZoMys6fS9QzYr0xhz4y/zHmK.3nK4xPqOVG2', 
     'Siti Nurhaliza', 'siti.nurhaliza@email.com', '082345678903', 'BUYER', NOW(), NOW())
ON CONFLICT (username) DO NOTHING;

-- =============================================
-- 3. Dummy Properties (6 properties, 2 per seller)
-- =============================================
INSERT INTO properties (address, price, description, image_url, seller_id)
SELECT 
    'Jl. Sudirman No. 123, Jakarta Pusat', 
    2500000000, 
    'Rumah mewah 2 lantai dengan 4 kamar tidur, 3 kamar mandi, garasi 2 mobil, taman luas, dan kolam renang. Lokasi strategis dekat pusat bisnis.',
    'https://images.unsplash.com/photo-1564013799919-ab600027ffc6?w=800',
    user_id
FROM users WHERE username = 'seller1' LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO properties (address, price, description, image_url, seller_id)
SELECT 
    'Jl. Gatot Subroto No. 456, Jakarta Selatan', 
    1800000000, 
    'Apartemen modern fully furnished, 3 kamar tidur, view kota, fasilitas lengkap termasuk gym dan swimming pool.',
    'https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=800',
    user_id
FROM users WHERE username = 'seller1' LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO properties (address, price, description, image_url, seller_id)
SELECT 
    'Jl. Raya Bogor No. 789, Depok', 
    850000000, 
    'Rumah minimalis 1 lantai dengan 3 kamar tidur, 2 kamar mandi, carport, dan taman depan. Lingkungan asri dan aman.',
    'https://images.unsplash.com/photo-1518780664697-55e3ad937233?w=800',
    user_id
FROM users WHERE username = 'seller2' LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO properties (address, price, description, image_url, seller_id)
SELECT 
    'Jl. BSD Boulevard No. 321, Tangerang Selatan', 
    3200000000, 
    'Town house exclusive dengan 5 kamar tidur, smart home system, private garden, dan security 24 jam.',
    'https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?w=800',
    user_id
FROM users WHERE username = 'seller2' LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO properties (address, price, description, image_url, seller_id)
SELECT 
    'Jl. Kemang Raya No. 555, Jakarta Selatan', 
    4500000000, 
    'Villa mewah bergaya Bali dengan 6 kamar tidur, private pool, outdoor kitchen, dan pemandangan gunung.',
    'https://images.unsplash.com/photo-1600585154340-be6161a56a0c?w=800',
    user_id
FROM users WHERE username = 'seller3' LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO properties (address, price, description, image_url, seller_id)
SELECT 
    'Jl. Cilandak KKO No. 888, Jakarta Selatan', 
    1200000000, 
    'Ruko 3 lantai strategis untuk usaha, luas tanah 100m2, luas bangunan 250m2, parkir luas.',
    'https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?w=800',
    user_id
FROM users WHERE username = 'seller3' LIMIT 1
ON CONFLICT DO NOTHING;

-- =============================================
-- 4. Dummy Reviews (beberapa review dari buyers)
-- =============================================
INSERT INTO reviews (rating, comment, buyer_id, property_id, created_at)
SELECT 
    5,
    'Rumah sangat bagus dan sesuai dengan deskripsi. Penjual sangat responsif dan helpful. Highly recommended!',
    (SELECT user_id FROM users WHERE username = 'buyer1'),
    property_id,
    NOW()
FROM properties WHERE address LIKE '%Sudirman%' LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO reviews (rating, comment, buyer_id, property_id, created_at)
SELECT 
    4,
    'Apartemen nyaman dan fasilitas lengkap. Hanya saja parkir agak terbatas. Overall puas dengan pembelian ini.',
    (SELECT user_id FROM users WHERE username = 'buyer2'),
    property_id,
    NOW()
FROM properties WHERE address LIKE '%Gatot Subroto%' LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO reviews (rating, comment, buyer_id, property_id, created_at)
SELECT 
    5,
    'Lokasi strategis dan harga sangat reasonable. Proses transaksi lancar. Terima kasih!',
    (SELECT user_id FROM users WHERE username = 'buyer3'),
    property_id,
    NOW()
FROM properties WHERE address LIKE '%Bogor%' LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO reviews (rating, comment, buyer_id, property_id, created_at)
SELECT 
    4,
    'Town house mewah dengan fasilitas lengkap. Smart home system-nya sangat membantu. Sedikit masukan: finishing bisa lebih rapi.',
    (SELECT user_id FROM users WHERE username = 'buyer1'),
    property_id,
    NOW()
FROM properties WHERE address LIKE '%BSD%' LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO reviews (rating, comment, buyer_id, property_id, created_at)
SELECT 
    5,
    'Villa impian! Desain Bali-nya sangat cantik dan private pool-nya amazing. Worth every penny!',
    (SELECT user_id FROM users WHERE username = 'buyer2'),
    property_id,
    NOW()
FROM properties WHERE address LIKE '%Kemang%' LIMIT 1
ON CONFLICT DO NOTHING;
