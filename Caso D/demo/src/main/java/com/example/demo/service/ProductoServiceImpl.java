package com.example.demo.service;

import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> getProductoById(int id) {
        return productoRepository.findById(id);
    }

    @Override
    public boolean deleteProducto(int id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
