-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS taller_parcial_spring_boot_jpa;
USE taller_parcial_spring_boot_jpa;

-- Eliminar tablas si existen (en orden correcto por dependencias)
DROP TABLE IF EXISTS carrito_productos;
DROP TABLE IF EXISTS orden_productos;
DROP TABLE IF EXISTS comentarios;
DROP TABLE IF EXISTS carritos_compras;
DROP TABLE IF EXISTS ordenes_compra;
DROP TABLE IF EXISTS productos;
DROP TABLE IF EXISTS categorias;
DROP TABLE IF EXISTS usuarios;

-- Crear tabla usuarios
CREATE TABLE usuarios (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    correo_electronico VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    direccion VARCHAR(255),
    metodo_de_pago VARCHAR(50)
);

-- Crear tabla categorias
CREATE TABLE categorias (
    id_categoria INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL
);

-- Crear tabla productos
CREATE TABLE productos (
    id_producto INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    id_categoria INT,
    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria)
);

-- Crear tabla carritos_compras
CREATE TABLE carritos_compras (
    id_carrito INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    impuestos DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- Crear tabla ordenes_compra
CREATE TABLE ordenes_compra (
    id_orden INT PRIMARY KEY AUTO_INCREMENT,
    subtotal DECIMAL(10,2) NOT NULL,
    impuestos DECIMAL(10,2) NOT NULL,
    envio DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL
);

-- Crear tabla comentarios
CREATE TABLE comentarios (
    id_comentario INT PRIMARY KEY AUTO_INCREMENT,
    id_producto INT NOT NULL,
    id_usuario INT NOT NULL,
    comentario TEXT NOT NULL,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- Tabla de relación many-to-many entre carritos y productos
CREATE TABLE carrito_productos (
    id_carrito INT,
    id_producto INT,
    PRIMARY KEY (id_carrito, id_producto),
    FOREIGN KEY (id_carrito) REFERENCES carritos_compras(id_carrito),
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
);

-- Tabla de relación many-to-many entre ordenes y productos
CREATE TABLE orden_productos (
    id_orden INT,
    id_producto INT,
    PRIMARY KEY (id_orden, id_producto),
    FOREIGN KEY (id_orden) REFERENCES ordenes_compra(id_orden),
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
);