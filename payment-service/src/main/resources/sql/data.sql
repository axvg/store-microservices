INSERT INTO payments (id, order_id, amount, status, method, created_at)
VALUES
    (1, 1, 105.00, 'CONFIRMED', 'CARD', CURRENT_TIMESTAMP),
    (2, 2, 109.00, 'CREATED', 'YAPE', CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;

SELECT setval('payments_id_seq', COALESCE((SELECT MAX(id) FROM payments), 1), TRUE);
