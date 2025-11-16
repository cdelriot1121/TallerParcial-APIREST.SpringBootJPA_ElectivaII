package com.example.TallerParcialSpringBootJPA.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.TallerParcialSpringBootJPA.entities.CarritoCompras;
import com.example.TallerParcialSpringBootJPA.entities.CarritoProducto;
import com.example.TallerParcialSpringBootJPA.entities.Producto;
import com.example.TallerParcialSpringBootJPA.entities.Usuario;
import com.example.TallerParcialSpringBootJPA.repository.CarritoProductoRepository;
import com.example.TallerParcialSpringBootJPA.repository.CarritoRepository;

@Service
@Transactional
public class CarritoService {
    
    @Autowired
    private CarritoRepository carritoRepository;
    
    @Autowired
    private CarritoProductoRepository carritoProductoRepository;
    
    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    public List<CarritoCompras> findAll() {
        return carritoRepository.findAll();
    }
    
    public Optional<CarritoCompras> findById(Integer id) {
        return carritoRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<CarritoCompras> findByUsuarioId(Integer idUsuario) {
        List<CarritoCompras> carritos = carritoRepository.findByUsuarioIdUsuario(idUsuario);
        System.out.println("Usuario " + idUsuario + " tiene " + carritos.size() + " carritos");
        for (CarritoCompras carrito : carritos) {
            System.out.println("Carrito " + carrito.getIdCarrito() + " tiene " + 
                (carrito.getProductos() != null ? carrito.getProductos().size() : 0) + " productos");
        }
        return carritos;
    }
    
    public Optional<CarritoCompras> findByIdAndUsuarioId(Integer idCarrito, Integer idUsuario) {
        return carritoRepository.findByIdCarritoAndUsuarioIdUsuario(idCarrito, idUsuario);
    }
    
    public CarritoCompras crearCarrito(Integer idUsuario) {
        Optional<Usuario> usuarioOpt = usuarioService.findById(idUsuario);
        if (usuarioOpt.isPresent()) {
            CarritoCompras carrito = new CarritoCompras();
            carrito.setUsuario(usuarioOpt.get());
            carrito.setProductos(new ArrayList<>());
            carrito.setSubtotal(0.0);
            carrito.setImpuestos(0.0);
            return carritoRepository.save(carrito);
        }
        throw new RuntimeException("Usuario no encontrado");
    }
    
    @Transactional
    public boolean agregarProductoAlCarrito(Integer idCarrito, Integer idProducto, Integer cantidad, Integer idUsuario) {
        try {
            // Verificar que el carrito pertenece al usuario
            Optional<CarritoCompras> carritoOpt = carritoRepository.findByIdCarritoAndUsuarioIdUsuario(idCarrito, idUsuario);
            if (!carritoOpt.isPresent()) {
                System.out.println("Carrito no encontrado o no pertenece al usuario: " + idCarrito + ", " + idUsuario);
                return false;
            }
            
            CarritoCompras carrito = carritoOpt.get();
            Optional<Producto> productoOpt = productoService.findById(idProducto);
            
            if (productoOpt.isPresent()) {
                Producto producto = productoOpt.get();
                
                // Verificar stock disponible
                if (producto.getStock() >= cantidad) {
                    // Verificar si el producto ya está en el carrito
                    Optional<CarritoProducto> carritoProductoExistente = 
                        carritoProductoRepository.findByCarritoIdCarritoAndProductoIdProducto(idCarrito, idProducto);
                    
                    if (carritoProductoExistente.isPresent()) {
                        // El producto ya existe, actualizar cantidad
                        CarritoProducto carritoProducto = carritoProductoExistente.get();
                        Integer nuevaCantidad = carritoProducto.getCantidad() + cantidad;
                        
                        // Verificar que hay suficiente stock para la nueva cantidad
                        if (producto.getStock() >= nuevaCantidad - carritoProducto.getCantidad()) {
                            carritoProducto.setCantidad(nuevaCantidad);
                            carritoProductoRepository.save(carritoProducto);
                            
                            // Descontar solo la cantidad adicional del stock
                            productoService.descontarStock(idProducto, cantidad);
                            System.out.println("Cantidad actualizada para producto existente: " + nuevaCantidad);
                        } else {
                            System.out.println("Stock insuficiente para actualizar cantidad");
                            return false;
                        }
                    } else {
                        // Producto nuevo, crear nueva entrada
                        CarritoProducto carritoProducto = new CarritoProducto(carrito, producto, cantidad);
                        carritoProductoRepository.save(carritoProducto);
                        
                        // Descontar del stock
                        productoService.descontarStock(idProducto, cantidad);
                        System.out.println("Nuevo producto agregado al carrito con cantidad: " + cantidad);
                    }
                    
                    // Recalcular totales
                    calcularTotales(carrito);
                    carritoRepository.save(carrito);
                    
                    return true;
                } else {
                    System.out.println("Stock insuficiente. Stock disponible: " + producto.getStock() + ", cantidad solicitada: " + cantidad);
                }
            } else {
                System.out.println("Producto no encontrado: " + idProducto);
            }
        } catch (Exception e) {
            System.out.println("Error al agregar producto al carrito: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    @Transactional(readOnly = true)
    public List<Producto> listarProductosDelCarrito(Integer idCarrito, Integer idUsuario) {
        List<CarritoProducto> carritoProductos = carritoProductoRepository.findByCarritoIdCarritoAndUsuarioId(idCarrito, idUsuario);
        List<Producto> productos = new ArrayList<>();
        
        for (CarritoProducto cp : carritoProductos) {
            // Agregar el producto tantas veces como indique la cantidad
            for (int i = 0; i < cp.getCantidad(); i++) {
                productos.add(cp.getProducto());
            }
        }
        
        System.out.println("Carrito " + idCarrito + " tiene " + carritoProductos.size() + 
                           " tipos de productos diferentes, total items: " + productos.size());
        return productos;
    }
    
    private void calcularTotales(CarritoCompras carrito) {
        double subtotal = 0.0;
        
        // Usar la nueva relación CarritoProducto
        List<CarritoProducto> carritoProductos = carritoProductoRepository.findByCarritoIdCarrito(carrito.getIdCarrito());
        
        for (CarritoProducto cp : carritoProductos) {
            subtotal += cp.getProducto().getPrecio() * cp.getCantidad();
        }
        
        carrito.setSubtotal(subtotal);
        carrito.setImpuestos(subtotal * 0.19); // 19% de impuestos
    }
    
    public CarritoCompras save(CarritoCompras carrito) {
        return carritoRepository.save(carrito);
    }
    
    public void deleteById(Integer id) {
        carritoRepository.deleteById(id);
    }
    
    public boolean perteneceCarritoAUsuario(Integer idCarrito, Integer idUsuario) {
        return carritoRepository.existsByIdCarritoAndUsuarioIdUsuario(idCarrito, idUsuario);
    }
}
