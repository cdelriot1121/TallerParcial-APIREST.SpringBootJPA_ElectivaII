package com.example.TallerParcialSpringBootJPA.dto;

public class LoginRequest {
    private String correoElectronico;
    private String contraseña;
    
    public LoginRequest() {}
    
    public LoginRequest(String correoElectronico, String contraseña) {
        this.correoElectronico = correoElectronico;
        this.contraseña = contraseña;
    }
    
    // Getters y Setters
    public String getCorreoElectronico() {
        return correoElectronico;
    }
    
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    
    public String getContraseña() {
        return contraseña;
    }
    
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}