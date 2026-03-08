INSERT INTO orders (id, user_id, status, total_price, created_at)
VALUES
    (1, 2, 'CREATED', 105.00, CURRENT_TIMESTAMP),
    (2, 3, 'CONFIRMED', 109.00, CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;

INSERT INTO order_items (id, order_id, product_id, quantity, price)
VALUES
    (1, 1, 2, 2, 38.50),
    (2, 1, 5, 1, 28.00),
    (3, 2, 1, 1, 45.00),
    (4, 2, 3, 2, 32.00)
ON CONFLICT (id) DO NOTHING;

SELECT setval('orders_id_seq', COALESCE((SELECT MAX(id) FROM orders), 1), TRUE);
SELECT setval('order_items_id_seq', COALESCE((SELECT MAX(id) FROM order_items), 1), TRUE);
