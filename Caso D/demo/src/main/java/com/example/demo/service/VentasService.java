package com.example.demo.service;

import com.example.demo.model.Ventas;

import java.util.List;
import java.util.Optional;

public interface VentasService {
    List<Ventas> getAllVentas();

    Ventas saveVentas(Ventas ventas);

    Optional<Ventas> getVentasById(int id);

    boolean deleteVentas(int id);
}
