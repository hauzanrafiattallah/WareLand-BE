-- =============================================
-- V4: Fix Dummy Data Passwords (Again)
-- Password: Password123!
-- Valid BCrypt Hash generated: $2a$10$5zxy.ahgp/w0u3aQNEiaHOUemdFKlfhzZRgT0oipt1czpf6C/gUKq
-- =============================================

UPDATE users 
SET password = '$2a$10$5zxy.ahgp/w0u3aQNEiaHOUemdFKlfhzZRgT0oipt1czpf6C/gUKq'
WHERE username IN ('seller1', 'seller2', 'seller3', 'buyer1', 'buyer2', 'buyer3');
