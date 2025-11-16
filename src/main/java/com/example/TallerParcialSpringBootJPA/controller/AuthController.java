package com.example.TallerParcialSpringBootJPA.controller;

import com.example.TallerParcialSpringBootJPA.dto.JwtResponse;
import com.example.TallerParcialSpringBootJPA.dto.LoginRequest;
import com.example.TallerParcialSpringBootJPA.entities.Usuario;
import com.example.TallerParcialSpringBootJPA.security.JwtUtils;
import com.example.TallerParcialSpringBootJPA.security.UserPrincipal;
import com.example.TallerParcialSpringBootJPA.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getCorreoElectronico(),
                            loginRequest.getContraseña())
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            
            UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
            Usuario usuario = usuarioService.findByCorreoElectronico(userDetails.getUsername()).get();
            
            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    usuario.getNombre()));
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Credenciales inválidas");
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
}