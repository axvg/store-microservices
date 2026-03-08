INSERT INTO deliveries (id, order_id, address, status, estimated_time, driver_name, created_at, updated_at)
VALUES
    (1, 1, 'Calle 472 Miraflores', 'ASSIGNED', 45, 'Raul', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 2, 'Jr. de la Union 333, Lima', 'DELIVERED', 25, 'Kevin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;

SELECT setval('deliveries_id_seq', COALESCE((SELECT MAX(id) FROM deliveries), 1), TRUE);
