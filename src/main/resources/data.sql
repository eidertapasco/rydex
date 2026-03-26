-- ============================================================
-- DATOS DE PRUEBA - RYDEX
-- Se ejecuta automáticamente al iniciar con H2
-- ============================================================

-- ADMIN (password: admin123)
INSERT INTO clientes (nombre, documento, telefono, email, password, rol)
VALUES ('Admin Rydex', '000000000', '3000000000', 'admin@rydex.com',
        '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'ADMIN');

-- CLIENTE de prueba (password: cliente123)
INSERT INTO clientes (nombre, documento, telefono, email, password, rol)
VALUES ('Juan Pérez', '123456789', '3001234567', 'juan@email.com',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LkFPistia.C', 'CLIENTE');

-- PROVEEDOR
INSERT INTO proveedores (nombre_empresa, persona_contacto, telefono_contacto, email_contacto)
VALUES ('Trek Colombia', 'Carlos Ruiz', '6014567890', 'ventas@trekcolombia.com');

INSERT INTO proveedores (nombre_empresa, persona_contacto, telefono_contacto, email_contacto)
VALUES ('Giant Bikes', 'Ana Mora', '6019876543', 'contacto@giantbikes.co');

-- ============================================================
-- BICICLETAS MOUNTAIN
-- ============================================================
INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta)
VALUES ('MTB-TREK-001', 'Trek', 'Marlin 7', 'Mountain', 2800000, 8, 3, 'Bicicleta de montaña con suspensión delantera RockShox, cambios Shimano Deore 1x12.', 'NEW ARRIVAL');

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta)
VALUES ('MTB-SPEC-002', 'Specialized', 'Rockhopper Expert', 'Mountain', 3500000, 5, 2, 'Cuadro de aluminio FACT 3m, horquilla SR Suntour XCR, transmisión Shimano Deore.', NULL);

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta)
VALUES ('MTB-GIANT-003', 'Giant', 'Talon 1', 'Mountain', 2200000, 10, 4, 'Geometría agresiva para trail, frenos de disco hidráulicos Tektro, ruedas 29".', NULL);

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta)
VALUES ('MTB-SCOTT-004', 'Scott', 'Scale 940', 'Mountain', 4100000, 3, 2, 'Full-suspension con amortiguador trasero RockShox Judy, ideal para enduro.', 'LUXURY TIER');

-- ============================================================
-- BICICLETAS ROAD
-- ============================================================
INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta)
VALUES ('RD-TREK-001', 'Trek', 'Domane AL 3', 'Road', 3200000, 6, 2, 'Endurance road bike con cuadro aluminio, grupo Shimano Sora, ideal para largas distancias.', NULL);

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta)
VALUES ('RD-CANNO-002', 'Cannondale', 'CAAD13', 'Road', 5800000, 4, 2, 'Cuadro de aluminio de alto rendimiento, grupo Shimano 105, ruedas WTB ST i23.', 'LUXURY TIER');

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta)
VALUES ('RD-GIANT-003', 'Giant', 'Contend 3', 'Road', 2600000, 7, 3, 'Perfecta para iniciarse en ciclismo de ruta, cambios Shimano Claris, cuadro ALUXX.', 'NEW ARRIVAL');

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta)
VALUES ('RD-BIANCHI-004', 'Bianchi', 'Via Nirone 7', 'Road', 4400000, 2, 2, 'Icónica bicicleta italiana, cuadro Countervail, grupo Shimano Tiagra.', NULL);

-- ============================================================
-- BICICLETAS ELECTRIC
-- ============================================================
INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta)
VALUES ('EL-TREK-001', 'Trek', 'Verve+ 2', 'Electric', 8900000, 4, 2, 'E-bike urbana con motor Bosch Active Line Plus, autonomía hasta 130km, display integrado.', 'NEW ARRIVAL');

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta)
VALUES ('EL-SPEC-002', 'Specialized', 'Turbo Vado 3.0', 'Electric', 12500000, 2, 1, 'Motor SL 1.1 de 240W, autonomía 130km, MasterMind TCU con conectividad Bluetooth.', 'LUXURY TIER');

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta)
VALUES ('EL-GIANT-003', 'Giant', 'Explore E+ 2', 'Electric', 7800000, 5, 2, 'Motor SyncDrive Sport, batería EnergyPak 500Wh, suspensión delantera SR Suntour.', NULL);

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta)
VALUES ('EL-SCOTT-004', 'Scott', 'Sub Sport eRIDE 20', 'Electric', 9600000, 3, 2, 'Motor Bosch Performance CX Gen4, autonomía 120km, perfecta para commuting.', NULL);

-- ============================================================
-- BICICLETAS GEAR (Urban/Fixed Gear)
-- ============================================================
INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta)
VALUES ('GR-PURE-001', 'Pure Cycles', 'Original Fixed Gear', 'Gear', 1200000, 12, 5, 'Fixed gear clásica, cuadro cromoly, llanta doble pared, ideal para ciudad.', NULL);

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta)
VALUES ('GR-STATE-002', 'State Bicycle', 'Core Line', 'Gear', 1800000, 8, 3, 'Single speed premium, cuadro aluminio 6061, flip-flop hub, colores vibrantes.', 'NEW ARRIVAL');

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta)
VALUES ('GR-LEADER-003', 'Leader', '725TR', 'Gear', 2400000, 5, 2, 'Track geometry agresiva, cuadro aluminio 7005, buje Cuando, para pista y calle.', 'LUXURY TIER');

INSERT INTO bicicletas (sku, marca, modelo, tipo, precio, stock_actual, stock_minimo, descripcion, etiqueta)
VALUES ('GR-CINELLI-004', 'Cinelli', 'Tutto', 'Gear', 3100000, 3, 2, 'Ícono italiano del fixed gear, acero cromoly, geometría race, componentes Cinelli.', NULL);