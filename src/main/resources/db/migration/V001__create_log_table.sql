CREATE TABLE IF NOT EXISTS order_file_log (
    id UUID PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    file_hash VARCHAR(255) NOT NULL,
    file_type VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);