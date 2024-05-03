package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;

// HATEOAS
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "ventas")
public class Ventas extends RepresentationModel<Ventas> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "total")
    private double total;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "id_producto")
    private int idProducto;

    @Column(name = "id_vendedor")
    private int idVendedor;

    public Ventas() {
    }

    public Ventas(int id, int cantidad, double total, Date fecha, int idProducto, int idVendedor) {
        this.id = id;
        this.cantidad = cantidad;
        this.total = total;
        this.fecha = fecha;
        this.idProducto = idProducto;
        this.idVendedor = idVendedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }
}
