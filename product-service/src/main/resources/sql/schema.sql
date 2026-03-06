CREATE TABLE IF NOT EXISTS products (
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(200) NOT NULL,
	description TEXT,
	price NUMERIC(10, 2) NOT NULL,
	stock INTEGER NOT NULL,
	category VARCHAR(50),
	created_by BIGINT,
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_products_category ON products (category);
CREATE INDEX IF NOT EXISTS idx_products_created_by ON products (created_by);
CREATE INDEX IF NOT EXISTS idx_products_price ON products (price);
CREATE INDEX IF NOT EXISTS idx_products_stock ON products (stock);
CREATE INDEX IF NOT EXISTS idx_products_created_at ON products (created_at);
