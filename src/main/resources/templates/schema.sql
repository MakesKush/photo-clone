CREATE TABLE IF NOT EXISTS photos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    file_name VARCHAR(255),
    content_type VARCHAR(255),
    data BLOB
);