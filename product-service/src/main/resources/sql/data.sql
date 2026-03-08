INSERT INTO products (name, description, price, stock, category, created_by, created_at, updated_at)
VALUES
	('Lomo Saltado', 'Trozos de lomo fino salteados al wok con cebolla, tomate y ají amarillo. Incluye porción de papas fritas huamantanga y arroz blanco.', 45.00, 50, 'Platos de Fondo', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('Ceviche', 'Pesca del día marinada en jugo de limón de Chulucanas, ají limo fresco y cebolla roja. Acompañado de camote glaseado y choclo tierno.', 38.50, 30, 'Pescados y Mariscos', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('Ají de Gallina', 'Tradicional crema de ají amarillo con pechuga deshilachada, pecanas y queso parmesano. Servido con papa amarilla sancochada y arroz.', 32.00, 40, 'Platos de Fondo', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('Arroz con Pollo', 'Pierna de pollo cocinada lentamente sobre arroz graneado con culantro, chicha de jora, zanahoria y crema de huancaína.', 35.00, 20, 'Platos de Fondo', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('Anticuchos', 'Dos palitos de corazón de res marinados en salsa de ají panca, dorados a la parrilla. Salen con papa dorada y salsa de rocoto.', 28.00, 25, 'Entradas', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;
