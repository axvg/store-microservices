INSERT INTO roles (id, name, description, created_at)
VALUES
    (1, 'ROLE_ADMIN', 'Administrador del sistema', CURRENT_TIMESTAMP),
    (2, 'ROLE_USER', 'Cliente de la plataforma', CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;

INSERT INTO users (id, name, email, phone, address, password, enabled, created_at, updated_at)
VALUES
    (1, 'Carlos', 'cmendoza@mail.com', '999999999', 'Av. Javier Prado 1234, San Isidro', 'password1234', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'Luciana', 'lvargas@mail.com', '999999998', 'Calle 456, Miraflores', 'password1234', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'Jorge', 'jtapia99@mail.com', '999999997', 'Jr. de la Union 333, Lima', 'password1234', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;

INSERT INTO user_roles (user_id, role_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (3, 2)
ON CONFLICT (user_id, role_id) DO NOTHING;

SELECT setval('roles_id_seq', COALESCE((SELECT MAX(id) FROM roles), 1), TRUE);
SELECT setval('users_id_seq', COALESCE((SELECT MAX(id) FROM users), 1), TRUE);
