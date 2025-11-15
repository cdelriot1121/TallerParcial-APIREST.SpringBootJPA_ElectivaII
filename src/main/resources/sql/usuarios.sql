-- Insertar usuarios con contraseñas encriptadas (BCrypt)
INSERT INTO usuarios (id_usuario, nombre, correo_electronico, contrasena, direccion, metodo_de_pago) VALUES
(1, 'Juan Pérez', 'juan.perez@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeOALO8SkE0Vz3Rxt7Gb2QTH3h4H7Y3cW', 'Carrera 45 #10-20', 'Tarjeta de crédito'),
(2, 'Ana Gómez', 'ana.gomez@email.com', '$2a$10$F8fZN8JV8uWrXd2.RZ7zbeR8VmPQ9xUvQVnRkgX4VnBmHcQq8qMZ2', 'Calle 21 #35-50', 'PayPal'),
(3, 'Carlos Ruiz', 'carlos.ruiz@email.com', '$2a$10$QzKzP9N8y8K2N8x8V8Q2k.wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z', 'Avenida Principal #100', 'Transferencia bancaria'),
(4, 'Sofía Martínez', 'sofia.martinez@email.com', '$2a$10$M8N8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k', 'Calle 8 #20-30', 'Efectivo'),
(5, 'Diego Fernández', 'diego.fernandez@email.com', '$2a$10$L8N8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k', 'Carrera 77 #40-60', 'Tarjeta débito'),
(6, 'Lucía Rodríguez', 'lucia.rodriguez@email.com', '$2a$10$K8N8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k', 'Calle 15 #25-35', 'Tarjeta de crédito'),
(7, 'Andrés Ramírez', 'andres.ramirez@email.com', '$2a$10$J8N8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k', 'Carrera 30 #50-70', 'PayPal'),
(8, 'María García', 'maria.garcia@email.com', '$2a$10$I8N8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k', 'Avenida 80 #45-65', 'Transferencia bancaria'),
(9, 'Javier Martínez', 'javier.martinez@email.com', '$2a$10$H8N8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k', 'Calle 12 #18-28', 'Efectivo'),
(10, 'Carolina López', 'carolina.lopez@email.com', '$2a$10$G8N8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k', 'Carrera 60 #30-40', 'Tarjeta débito'),
(11, 'Daniel Castro', 'daniel.castro@email.com', '$2a$10$F8N8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k', 'Calle 25 #40-50', 'Tarjeta de crédito'),
(12, 'Paola Herrera', 'paola.herrera@email.com', '$2a$10$E8N8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k', 'Avenida 70 #20-30', 'PayPal'),
(13, 'Esteban Rojas', 'esteban.rojas@email.com', '$2a$10$D8N8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k', 'Carrera 85 #15-25', 'Transferencia bancaria'),
(14, 'Fernanda Sánchez', 'fernanda.sanchez@email.com', '$2a$10$C8N8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k', 'Calle 35 #55-65', 'Efectivo'),
(15, 'Camilo Torres', 'camilo.torres@email.com', '$2a$10$B8N8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k8wQ8Z8Q2k', 'Carrera 95 #75-85', 'Tarjeta débito');