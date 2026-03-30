-- ============================================================
-- DATOS DE PRUEBA - RYDEX
-- ============================================================



-- PROVEEDORES
INSERT INTO proveedores (nombre_empresa, persona_contacto, telefono_contacto, email_contacto)
VALUES ('Trek Colombia', 'Carlos Ruiz', '6014567890', 'ventas@trekcolombia.com');

INSERT INTO proveedores (nombre_empresa, persona_contacto, telefono_contacto, email_contacto)
VALUES ('Giant Bikes', 'Ana Mora', '6019876543', 'contacto@giantbikes.co');

-- BICICLETAS MOUNTAIN
INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta, imagen_url)
VALUES ('MTB-TREK-001', 'Trek', 'Marlin 7', 'Mountain', 2800000, 8, 3, 'Bicicleta de montaña con suspensión delantera RockShox, cambios Shimano Deore 1x12.', 'NEW ARRIVAL', '/images/trek-marlin7.webp');

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta, imagen_url)
VALUES ('MTB-SPEC-002', 'Specialized', 'Rockhopper Expert', 'Mountain', 3500000, 5, 2, 'Cuadro de aluminio FACT 3m, horquilla SR Suntour XCR, transmisión Shimano Deore.', NULL, '/placeholder-bike.jpg');

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta, imagen_url)
VALUES ('MTB-GIANT-003', 'Giant', 'Talon 1', 'Mountain', 2200000, 10, 4, 'Geometría agresiva para trail, frenos de disco hidráulicos Tektro, ruedas 29".', NULL, '/placeholder-bike.jpg');

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta, imagen_url)
VALUES ('MTB-SCOTT-004', 'Scott', 'Scale 940', 'Mountain', 4100000, 3, 2, 'Full-suspension con amortiguador trasero RockShox Judy, ideal para enduro.', 'LUXURY TIER', '/placeholder-bike.jpg');

-- BICICLETAS ROAD
INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta, imagen_url)
VALUES ('RD-TREK-001', 'Trek', 'Domane AL 3', 'Road', 3200000, 6, 2, 'Endurance road bike con cuadro aluminio, grupo Shimano Sora, ideal para largas distancias.', NULL, '/placeholder-bike.jpg');

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta, imagen_url)
VALUES ('RD-CANNO-002', 'Cannondale', 'CAAD13', 'Road', 5800000, 4, 2, 'Cuadro de aluminio de alto rendimiento, grupo Shimano 105, ruedas WTB ST i23.', 'LUXURY TIER', '/placeholder-bike.jpg');

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta, imagen_url)
VALUES ('RD-GIANT-003', 'Giant', 'Contend 3', 'Road', 2600000, 7, 3, 'Perfecta para iniciarse en ciclismo de ruta, cambios Shimano Claris, cuadro ALUXX.', 'NEW ARRIVAL', '/placeholder-bike.jpg');

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta, imagen_url)
VALUES ('RD-BIANCHI-004', 'Bianchi', 'Via Nirone 7', 'Road', 4400000, 2, 2, 'Icónica bicicleta italiana, cuadro Countervail, grupo Shimano Tiagra.', NULL, '/placeholder-bike.jpg');

-- BICICLETAS ELECTRIC
INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta, imagen_url)
VALUES ('EL-TREK-001', 'Trek', 'Verve+ 2', 'Electric', 8900000, 4, 2, 'E-bike urbana con motor Bosch Active Line Plus, autonomía hasta 130km, display integrado.', 'NEW ARRIVAL', '/placeholder-bike.jpg');

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta, imagen_url)
VALUES ('EL-SPEC-002', 'Specialized', 'Turbo Vado 3.0', 'Electric', 12500000, 2, 1, 'Motor SL 1.1 de 240W, autonomía 130km, MasterMind TCU con conectividad Bluetooth.', 'LUXURY TIER', '/placeholder-bike.jpg');

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta, imagen_url)
VALUES ('EL-GIANT-003', 'Giant', 'Explore E+ 2', 'Electric', 7800000, 5, 2, 'Motor SyncDrive Sport, batería EnergyPak 500Wh, suspensión delantera SR Suntour.', NULL, '/placeholder-bike.jpg');

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta, imagen_url)
VALUES ('EL-SCOTT-004', 'Scott', 'Sub Sport eRIDE 20', 'Electric', 9600000, 3, 2, 'Motor Bosch Performance CX Gen4, autonomía 120km, perfecta para commuting.', NULL, '/placeholder-bike.jpg');

-- BICICLETAS GEAR
INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta, imagen_url)
VALUES ('GR-PURE-001', 'Pure Cycles', 'Original Fixed Gear', 'Gear', 1200000, 12, 5, 'Fixed gear clásica, cuadro cromoly, llanta doble pared, ideal para ciudad.', NULL, '/placeholder-bike.jpg');

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta, imagen_url)
VALUES ('GR-STATE-002', 'State Bicycle', 'Core Line', 'Gear', 1800000, 8, 3, 'Single speed premium, cuadro aluminio 6061, flip-flop hub, colores vibrantes.', 'NEW ARRIVAL', '/placeholder-bike.jpg');

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta, imagen_url)
VALUES ('GR-LEADER-003', 'Leader', '725TR', 'Gear', 2400000, 5, 2, 'Track geometry agresiva, cuadro aluminio 7005, buje Cuando, para pista y calle.', 'LUXURY TIER', '/placeholder-bike.jpg');

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta, imagen_url)
VALUES ('GR-CINELLI-004', 'Cinelli', 'Tutto', 'Gear', 3100000, 3, 2, 'Ícono italiano del fixed gear, acero cromoly, geometría race, componentes Cinelli.', NULL, '/placeholder-bike.jpg');