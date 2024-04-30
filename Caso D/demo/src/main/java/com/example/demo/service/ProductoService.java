package com.example.demo.service;

import com.example.demo.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> getAllProductos();

    Producto saveProducto(Producto producto);

    Optional<Producto> getProductoById(int id);

    boolean deleteProducto(int id);
}
