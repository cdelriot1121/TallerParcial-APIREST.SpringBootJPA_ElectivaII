package com.example.TallerParcialSpringBootJPA.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.TallerParcialSpringBootJPA.entities.CarritoCompras;
import com.example.TallerParcialSpringBootJPA.entities.Producto;
import com.example.TallerParcialSpringBootJPA.entities.Usuario;
import com.example.TallerParcialSpringBootJPA.repository.CarritoRepository;

@Service
@Transactional
public class CarritoService {
    
    @Autowired
    private CarritoRepository carritoRepository;
    
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
                    // Descontar del stock
                    if (productoService.descontarStock(idProducto, cantidad)) {
                        // Agregar al carrito
                        if (carrito.getProductos() == null) {
                            carrito.setProductos(new ArrayList<>());
                        }
                        
                        // Agregar el producto la cantidad de veces especificada
                        for (int i = 0; i < cantidad; i++) {
                            carrito.getProductos().add(producto);
                        }
                        
                        // Recalcular totales
                        calcularTotales(carrito);
                        
                        // Guardar el carrito
                        CarritoCompras carritoGuardado = carritoRepository.save(carrito);
                        System.out.println("Carrito guardado con " + carritoGuardado.getProductos().size() + " productos");
                        
                        return true;
                    } else {
                        System.out.println("No se pudo descontar el stock del producto: " + idProducto);
                    }
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
        Optional<CarritoCompras> carritoOpt = carritoRepository.findByIdCarritoAndUsuarioIdUsuario(idCarrito, idUsuario);
        if (carritoOpt.isPresent()) {
            CarritoCompras carrito = carritoOpt.get();
            List<Producto> productos = carrito.getProductos();
            System.out.println("Carrito " + idCarrito + " tiene " + (productos != null ? productos.size() : 0) + " productos");
            return productos != null ? productos : new ArrayList<>();
        }
        System.out.println("Carrito no encontrado: " + idCarrito + " para usuario: " + idUsuario);
        return new ArrayList<>();
    }
    
    private void calcularTotales(CarritoCompras carrito) {
        double subtotal = 0.0;
        if (carrito.getProductos() != null) {
            for (Producto producto : carrito.getProductos()) {
                subtotal += producto.getPrecio();
            }
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
