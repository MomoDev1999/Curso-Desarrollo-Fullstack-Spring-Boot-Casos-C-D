package com.example.demo.controller;

import com.example.demo.model.Ventas;
import com.example.demo.service.VentasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ventas")
public class VentasController {

    @Autowired
    private VentasService ventasService;

    @GetMapping
    public List<Ventas> getAllVentas() {
        return ventasService.getAllVentas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ventas> getVentasById(@PathVariable int id) {
        Optional<Ventas> ventas = ventasService.getVentasById(id);
        return ventas.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Ventas> createVentas(@RequestBody Ventas ventas) {
        Ventas nuevasVentas = ventasService.saveVentas(ventas);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevasVentas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ventas> updateVentas(@PathVariable int id, @RequestBody Ventas updatedVentas) {
        Optional<Ventas> existingVentas = ventasService.getVentasById(id);

        if (existingVentas.isPresent()) {
            Ventas ventas = existingVentas.get();
            // Actualizar los campos de las ventas con los datos recibidos
            ventas.setCantidad(updatedVentas.getCantidad());
            ventas.setTotal(updatedVentas.getTotal());
            ventas.setFecha(updatedVentas.getFecha());
            ventas.setIdProducto(updatedVentas.getIdProducto());
            ventas.setIdVendedor(updatedVentas.getIdVendedor());

            // Guardar las ventas actualizadas en la base de datos
            Ventas updated = ventasService.saveVentas(ventas);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVentas(@PathVariable int id) {
        boolean deleted = ventasService.deleteVentas(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/ganancias-diarias")
    public ResponseEntity<?> obtenerGananciasDiarias() {
        List<Ventas> ventas = ventasService.getAllVentas();
        double gananciasDiarias = 0;
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaActualFormateada = fechaActual.format(formatter);

        for (Ventas venta : ventas) {
            if (venta.getFecha().equals(fechaActualFormateada)) {
                gananciasDiarias += venta.getTotal();
            }
        }
        return ResponseEntity.ok(gananciasDiarias);
    }

    @GetMapping("/ganancias-mensuales")
    public ResponseEntity<?> obtenerGananciasMensuales() {
        List<Ventas> ventas = ventasService.getAllVentas();
        double gananciasMensuales = 0;
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String fechaActualFormateada = fechaActual.format(formatter);

        for (Ventas venta : ventas) {
            if (venta.getFecha().startsWith(fechaActualFormateada)) {
                gananciasMensuales += venta.getTotal();
            }
        }
        return ResponseEntity.ok(gananciasMensuales);
    }

    @GetMapping("/ganancias-anuales")
    public ResponseEntity<?> obtenerGananciasAnuales() {
        List<Ventas> ventas = ventasService.getAllVentas();
        double gananciasAnuales = 0;
        LocalDate fechaActual = LocalDate.now();
        int añoActual = fechaActual.getYear();

        for (Ventas venta : ventas) {
            try {
                LocalDate fechaVenta = LocalDate.parse(venta.getFecha());
                int añoVenta = fechaVenta.getYear();

                if (añoVenta == añoActual) {
                    gananciasAnuales += venta.getTotal();
                }
            } catch (DateTimeParseException e) {
                System.err.println("Error al analizar la fecha de la venta: " + e.getMessage());
            }
        }
        return ResponseEntity.ok(gananciasAnuales);
    }
}
