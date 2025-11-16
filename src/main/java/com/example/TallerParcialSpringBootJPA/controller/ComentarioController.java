package com.example.TallerParcialSpringBootJPA.controller;

import com.example.TallerParcialSpringBootJPA.entities.Comentarios;
import com.example.TallerParcialSpringBootJPA.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comentarios")
@CrossOrigin(origins = "*")
public class ComentarioController {
    
    @Autowired
    private ComentarioService comentarioService;
    
    // Endpoint público: Listar comentarios desde una fecha en adelante
    @GetMapping("/listar")
    public ResponseEntity<Map<String, Object>> listarComentariosPorFecha(@RequestParam String fechaDesde) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Comentarios> comentarios = comentarioService.findComentariosByFechaDesde(fechaDesde);
            response.put("comentarios", comentarios);
            response.put("mensaje", "Comentarios desde la fecha " + fechaDesde);
            response.put("total", comentarios.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Error al obtener comentarios: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // Endpoint protegido: Obtener todos los comentarios
    @GetMapping("/todos")
    public ResponseEntity<Map<String, Object>> listarTodosLosComentarios() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Comentarios> comentarios = comentarioService.findAll();
            response.put("comentarios", comentarios);
            response.put("total", comentarios.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Error al obtener comentarios: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // Endpoint protegido: Obtener comentarios por producto
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<Map<String, Object>> listarComentariosPorProducto(@PathVariable Integer idProducto) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Comentarios> comentarios = comentarioService.findByProductoId(idProducto);
            response.put("comentarios", comentarios);
            response.put("mensaje", "Comentarios del producto ID " + idProducto);
            response.put("total", comentarios.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Error al obtener comentarios: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // Endpoint protegido: Crear comentario
    @PostMapping
    public ResponseEntity<Map<String, Object>> crearComentario(@RequestBody Comentarios comentario) {
        Map<String, Object> response = new HashMap<>();
        try {
            Comentarios nuevoComentario = comentarioService.save(comentario);
            response.put("comentario", nuevoComentario);
            response.put("mensaje", "Comentario creado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Error al crear comentario: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
