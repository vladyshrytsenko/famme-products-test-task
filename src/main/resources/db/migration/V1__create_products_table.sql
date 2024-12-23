
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    handle VARCHAR(255),
    vendor VARCHAR(255),
    product_type VARCHAR(255),
    created_at TIMESTAMP
);
