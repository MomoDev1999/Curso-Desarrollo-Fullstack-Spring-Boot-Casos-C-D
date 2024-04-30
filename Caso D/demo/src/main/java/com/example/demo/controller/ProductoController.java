package com.example.demo.controller;

import com.example.demo.model.Producto;
import com.example.demo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> getAllProductos() {
        return productoService.getAllProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable int id) {
        Optional<Producto> producto = productoService.getProductoById(id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.saveProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable int id, @RequestBody Producto updatedProducto) {
        Optional<Producto> existingProducto = productoService.getProductoById(id);

        if (existingProducto.isPresent()) {
            Producto producto = existingProducto.get();
            // Actualizar los campos del producto con los datos recibidos
            producto.setNombre(updatedProducto.getNombre());
            producto.setPrecio(updatedProducto.getPrecio());

            // Guardar el producto actualizado en la base de datos
            Producto updated = productoService.saveProducto(producto);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable int id) {
        boolean deleted = productoService.deleteProducto(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
