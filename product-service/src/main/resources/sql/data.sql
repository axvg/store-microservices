INSERT INTO products (name, description, price, stock, category, created_by, created_at, updated_at)
VALUES
	('Gaming Laptop', 'High performance laptop for gaming and development', 1599.99, 12, 'Electronics', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('Wireless Mouse', 'Ergonomic 2.4G wireless mouse', 29.90, 60, 'Accessories', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('Mechanical Keyboard', 'RGB mechanical keyboard with blue switches', 89.50, 25, 'Accessories', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('27 Inch Monitor', '4K IPS monitor for productivity', 329.00, 0, 'Electronics', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('USB-C Hub', '7-in-1 USB-C hub with HDMI and PD', 49.99, 40, 'Accessories', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;
