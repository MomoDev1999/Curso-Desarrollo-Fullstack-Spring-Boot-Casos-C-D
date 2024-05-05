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
@Table(name = "calificacion")
public class Calificacion extends RepresentationModel<Calificacion> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "calificacion")
    private int calificacion;
    @Column(name = "id_publicacion")
    private int idPublicacion;
    @Column(name = "realizada_por")
    private String realizadaPor;
    @Column(name = "fecha")
    private String fecha;

    public Calificacion(int id, int calificacion, int idPublicacion, String realizadaPor, String fecha) {
        this.id = id;
        this.calificacion = calificacion;
        this.idPublicacion = idPublicacion;
        this.realizadaPor = realizadaPor;
        this.fecha = fecha;
    }

    public Calificacion() {
        // Constructor por defecto sin argumentos
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public String getRealizadaPor() {
        return realizadaPor;
    }

    public String getFecha() {
        return fecha;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public void setRealizadaPor(String realizadaPor) {
        this.realizadaPor = realizadaPor;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
