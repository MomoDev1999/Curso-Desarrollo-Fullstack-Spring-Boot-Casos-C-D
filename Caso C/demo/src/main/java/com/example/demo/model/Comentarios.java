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
@Table(name = "comentarios")
public class Comentarios extends RepresentationModel<Comentarios> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "id_publicacion")
    private int idPublicacion;

    @Column(name = "realizado_por")
    private String realizadoPor;

    @Column(name = "fecha")
    private String fecha;

    public Comentarios(int id, String comentario, int idPublicacion, String realizadoPor, String fecha) {
        this.id = id;
        this.comentario = comentario;
        this.idPublicacion = idPublicacion;
        this.realizadoPor = realizadoPor;
        this.fecha = fecha;
    }

    public Comentarios() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public String getRealizadoPor() {
        return realizadoPor;
    }

    public void setRealizadoPor(String realizadoPor) {
        this.realizadoPor = realizadoPor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
