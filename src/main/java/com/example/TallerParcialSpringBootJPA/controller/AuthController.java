package com.example.TallerParcialSpringBootJPA.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TallerParcialSpringBootJPA.dto.JwtResponse;
import com.example.TallerParcialSpringBootJPA.dto.LoginRequest;
import com.example.TallerParcialSpringBootJPA.entities.Usuario;
import com.example.TallerParcialSpringBootJPA.security.JwtUtils;
import com.example.TallerParcialSpringBootJPA.security.UserPrincipal;
import com.example.TallerParcialSpringBootJPA.service.UsuarioService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    UsuarioService usuarioService;
    
    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("=== DEBUG LOGIN COMPLETO ===");
            System.out.println("Email recibido: " + loginRequest.getCorreoElectronico());
            System.out.println("Contraseña recibida: " + loginRequest.getContraseña());
            
            // Verificar si el usuario existe y obtener su hash
            Usuario usuario = usuarioService.findByCorreoElectronico(loginRequest.getCorreoElectronico()).orElse(null);
            if (usuario != null) {
                System.out.println("Usuario encontrado en AuthController: " + usuario.getNombre());
                System.out.println("Hash en DB: " + usuario.getContraseña());
                
                // Verificar si la contraseña coincide manualmente
                boolean passwordMatches = passwordEncoder.matches(loginRequest.getContraseña(), usuario.getContraseña());
                System.out.println("¿Contraseña coincide? " + passwordMatches);
                
                if (!passwordMatches) {
                    System.out.println("ERROR: La contraseña no coincide con el hash!");
                    // Generar nuevo hash para comparar
                    String newHash = passwordEncoder.encode(loginRequest.getContraseña());
                    System.out.println("Nuevo hash generado: " + newHash);
                }
            } else {
                System.out.println("ERROR: Usuario no encontrado!");
            }
            
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getCorreoElectronico(),
                            loginRequest.getContraseña())
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            
            UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
            
            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    usuario.getNombre()));
        } catch (Exception e) {
            System.out.println("ERROR en login: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Credenciales inválidas");
            error.put("detalle", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody Usuario usuario) {
        Map<String, String> response = new HashMap<>();
        
        if (usuarioService.existsByCorreoElectronico(usuario.getCorreoElectronico())) {
            response.put("error", "El correo electrónico ya está en uso");
            return ResponseEntity.badRequest().body(response);
        }
        
        // Crear nuevo usuario
        Usuario nuevoUsuario = usuarioService.save(usuario);
        
        response.put("message", "Usuario registrado exitosamente");
        response.put("id", nuevoUsuario.getIdUsuario().toString());
        return ResponseEntity.ok(response);
    }
    
    // Endpoint temporal para debug - remover en producción
    @PostMapping("/debug-password")
    public ResponseEntity<?> debugPassword(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        String email = request.get("email");
        String password = request.get("password");
        
        Usuario usuario = usuarioService.findByCorreoElectronico(email).orElse(null);
        
        if (usuario == null) {
            response.put("error", "Usuario no encontrado");
            return ResponseEntity.badRequest().body(response);
        }
        
        String hashInDb = usuario.getContraseña();
        boolean matches = passwordEncoder.matches(password, hashInDb);
        String newHash = passwordEncoder.encode(password);
        
        response.put("usuario", usuario.getNombre());
        response.put("email", email);
        response.put("passwordProvided", password);
        response.put("hashInDatabase", hashInDb);
        response.put("passwordMatches", matches);
        response.put("newHashGenerated", newHash);
        response.put("hashStartsWithBcrypt", hashInDb.startsWith("$2a$"));
        response.put("hashLength", hashInDb.length());
        
        return ResponseEntity.ok(response);
    }
}