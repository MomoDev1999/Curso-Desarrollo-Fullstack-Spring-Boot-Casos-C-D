package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// HATEOAS
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "usuario")
public class Usuario extends RepresentationModel<Usuario> {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "edad")
    private int edad;

    // Constructor
    public Usuario() {
        // Constructor por defecto necesario para JPA
    }

    public Usuario(String username, String nombre, String apellido, int edad) {
        this.username = username;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
