-- Insertar usuarios con contraseñas encriptadas (BCrypt) - contraseñas específicas para cada usuario
INSERT INTO usuarios (id_usuario, nombre, correo_electronico, contrasena, direccion, metodo_de_pago) VALUES
(1, 'Juan Pérez', 'juan.perez@email.com', '$2a$10$Lm7ywlyxllHX7kySFkUsgecWLpuMNDJ3U/mj33Wqs5DNaEchbH54m', 'Carrera 45 #10-20', 'Tarjeta de crédito'),
(2, 'Ana Gómez', 'ana.gomez@email.com', '$2a$10$hdHVcGmUyP95X/n.Uq5iHOAx5lNBmWDydgTWO9syXfZ.MUZ7EEP3K', 'Calle 21 #35-50', 'PayPal'),
(3, 'Carlos Ruiz', 'carlos.ruiz@email.com', '$2a$10$0i/3P9ys4v78Cow19mkLDev6s9aejSlzCdBDoG3EH6gEPLE5Y4vLG', 'Avenida Principal #100', 'Transferencia bancaria'),
(4, 'Sofía Martínez', 'sofia.martinez@email.com', '$2a$10$nZ9tgm3jx3RD2dTl/QHYjuIG14f.OTdg68maSyi6crGRMVnj9a1s6', 'Calle 8 #20-30', 'Efectivo'),
(5, 'Diego Fernández', 'diego.fernandez@email.com', '$2a$10$rkogwfEJMKeT2xhRWQNiT.5/iiSFRoaaFFRIKoEUu5KfPT6fmpZku', 'Carrera 77 #40-60', 'Tarjeta débito'),
(6, 'Lucía Rodríguez', 'lucia.rodriguez@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeOALO8SkE0Vz3Rxt7Gb2QTH3h4H7Y3cW', 'Calle 15 #25-35', 'Tarjeta de crédito'),
(7, 'Andrés Ramírez', 'andres.ramirez@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeOALO8SkE0Vz3Rxt7Gb2QTH3h4H7Y3cW', 'Carrera 30 #50-70', 'PayPal'),
(8, 'María García', 'maria.garcia@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeOALO8SkE0Vz3Rxt7Gb2QTH3h4H7Y3cW', 'Avenida 80 #45-65', 'Transferencia bancaria'),
(9, 'Javier Martínez', 'javier.martinez@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeOALO8SkE0Vz3Rxt7Gb2QTH3h4H7Y3cW', 'Calle 12 #18-28', 'Efectivo'),
(10, 'Carolina López', 'carolina.lopez@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeOALO8SkE0Vz3Rxt7Gb2QTH3h4H7Y3cW', 'Carrera 60 #30-40', 'Tarjeta débito'),
(11, 'Daniel Castro', 'daniel.castro@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeOALO8SkE0Vz3Rxt7Gb2QTH3h4H7Y3cW', 'Calle 25 #40-50', 'Tarjeta de crédito'),
(12, 'Paola Herrera', 'paola.herrera@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeOALO8SkE0Vz3Rxt7Gb2QTH3h4H7Y3cW', 'Avenida 70 #20-30', 'PayPal'),
(13, 'Esteban Rojas', 'esteban.rojas@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeOALO8SkE0Vz3Rxt7Gb2QTH3h4H7Y3cW', 'Carrera 85 #15-25', 'Transferencia bancaria'),
(14, 'Fernanda Sánchez', 'fernanda.sanchez@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeOALO8SkE0Vz3Rxt7Gb2QTH3h4H7Y3cW', 'Calle 35 #55-65', 'Efectivo'),
(15, 'Camilo Torres', 'camilo.torres@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeOALO8SkE0Vz3Rxt7Gb2QTH3h4H7Y3cW', 'Carrera 95 #75-85', 'Tarjeta débito');