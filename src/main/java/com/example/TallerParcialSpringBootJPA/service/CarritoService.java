package com.example.TallerParcialSpringBootJPA.service;

import com.example.TallerParcialSpringBootJPA.entities.CarritoCompras;
import com.example.TallerParcialSpringBootJPA.entities.Producto;
import com.example.TallerParcialSpringBootJPA.entities.Usuario;
import com.example.TallerParcialSpringBootJPA.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    
    public List<CarritoCompras> findByUsuarioId(Integer idUsuario) {
        return carritoRepository.findByUsuarioIdUsuario(idUsuario);
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
    
    public boolean agregarProductoAlCarrito(Integer idCarrito, Integer idProducto, Integer cantidad, Integer idUsuario) {
        // Verificar que el carrito pertenece al usuario
        Optional<CarritoCompras> carritoOpt = carritoRepository.findByIdCarritoAndUsuarioIdUsuario(idCarrito, idUsuario);
        if (!carritoOpt.isPresent()) {
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
                    // Agregar al carrito (o actualizar cantidad si ya existe)
                    if (carrito.getProductos() == null) {
                        carrito.setProductos(new ArrayList<>());
                    }
                    carrito.getProductos().add(producto);
                    
                    // Recalcular totales
                    calcularTotales(carrito);
                    carritoRepository.save(carrito);
                    return true;
                }
            }
        }
        return false;
    }
    
    public List<Producto> listarProductosDelCarrito(Integer idCarrito, Integer idUsuario) {
        Optional<CarritoCompras> carritoOpt = carritoRepository.findByIdCarritoAndUsuarioIdUsuario(idCarrito, idUsuario);
        if (carritoOpt.isPresent()) {
            CarritoCompras carrito = carritoOpt.get();
            return carrito.getProductos() != null ? carrito.getProductos() : new ArrayList<>();
        }
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
