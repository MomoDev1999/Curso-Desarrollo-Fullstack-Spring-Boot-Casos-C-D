package com.example.demo.controller;

import com.example.demo.model.Vendedor;
import com.example.demo.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vendedores")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    @GetMapping
    public List<Vendedor> getAllVendedores() {
        return vendedorService.getAllVendedores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendedor> getVendedorById(@PathVariable int id) {
        Optional<Vendedor> vendedor = vendedorService.getVendedorById(id);
        return vendedor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Vendedor> createVendedor(@RequestBody Vendedor vendedor) {
        Vendedor nuevoVendedor = vendedorService.saveVendedor(vendedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoVendedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendedor> updateVendedor(@PathVariable int id, @RequestBody Vendedor updatedVendedor) {
        Optional<Vendedor> existingVendedor = vendedorService.getVendedorById(id);

        if (existingVendedor.isPresent()) {
            Vendedor vendedor = existingVendedor.get();
            // Actualizar los campos del vendedor con los datos recibidos
            vendedor.setNombre(updatedVendedor.getNombre());
            vendedor.setApellido(updatedVendedor.getApellido());
            vendedor.setRut(updatedVendedor.getRut());
            vendedor.setEdad(updatedVendedor.getEdad());

            // Guardar el vendedor actualizado en la base de datos
            Vendedor updated = vendedorService.saveVendedor(vendedor);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendedor(@PathVariable int id) {
        boolean deleted = vendedorService.deleteVendedor(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
