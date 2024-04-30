package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// HATEOAS
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "publicacion")
public class Publicacion extends RepresentationModel<Publicacion> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "autor")
    private String autor;

    @Column(name = "fecha")
    private String fecha;

    @Column(name = "descripcion")
    private String descripcion;

    public Publicacion() {
        // Constructor por defecto necesario para JPA
    }

    public Publicacion(Integer id, String titulo, String autor, String fecha, String descripcion) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
