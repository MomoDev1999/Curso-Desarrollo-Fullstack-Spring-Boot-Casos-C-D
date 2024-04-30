package com.example.demo.service;

import com.example.demo.model.Vendedor;

import java.util.List;
import java.util.Optional;

public interface VendedorService {
    List<Vendedor> getAllVendedores();

    Vendedor saveVendedor(Vendedor vendedor);

    Optional<Vendedor> getVendedorById(int id);

    boolean deleteVendedor(int id);
}
