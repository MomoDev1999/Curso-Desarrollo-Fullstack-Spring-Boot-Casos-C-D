package com.example.demo.controller;

import com.example.demo.model.Ventas;
import com.example.demo.service.VentasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ventas")
public class VentasController {

    @Autowired
    private VentasService ventasService;

    @GetMapping
    public CollectionModel<EntityModel<Ventas>> getAllVentas() {
        List<Ventas> ventas = ventasService.getAllVentas();
        List<EntityModel<Ventas>> ventasResources = ventas.stream()
                .map(venta -> EntityModel.of(venta,
                        WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder.methodOn(VentasController.class).getVentasById(venta.getId()))
                                .withSelfRel()))
                .collect(Collectors.toList());
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(VentasController.class).getAllVentas());
        CollectionModel<EntityModel<Ventas>> resources = CollectionModel.of(ventasResources, linkTo.withRel("ventas"));
        return resources;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Ventas>> getVentasById(@PathVariable int id) {
        Optional<Ventas> ventasOptional = ventasService.getVentasById(id);
        if (ventasOptional.isPresent()) {
            Ventas ventas = ventasOptional.get();
            EntityModel<Ventas> resource = EntityModel.of(ventas,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VentasController.class).getVentasById(id))
                            .withSelfRel());
            return ResponseEntity.ok(resource);
        } else {
            throw new VentasNotFoundException("Registro de ventas no encontrado con id: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Ventas>> createVentas(@RequestBody Ventas ventas) {
        // Obtener la fecha actual
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        // Establecer la fecha actual en la venta
        ventas.setFecha(sqlDate);

        // Guardar la venta en la base de datos
        Ventas nuevasVentas = ventasService.saveVentas(ventas);

        // Crear el recurso de la venta con HATEOAS
        EntityModel<Ventas> resource = EntityModel.of(nuevasVentas,
                WebMvcLinkBuilder
                        .linkTo(WebMvcLinkBuilder.methodOn(VentasController.class).getVentasById(nuevasVentas.getId()))
                        .withSelfRel());

        // Retornar la respuesta con el recurso creado y el código de estado 201
        // (CREATED)
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Ventas>> updateVentas(@PathVariable int id, @RequestBody Ventas updatedVentas) {
        Optional<Ventas> existingVentas = ventasService.getVentasById(id);

        if (existingVentas.isPresent()) {
            Ventas ventas = existingVentas.get();
            // Actualizar los campos de las ventas con los datos recibidos
            ventas.setCantidad(updatedVentas.getCantidad());
            ventas.setTotal(updatedVentas.getTotal());
            ventas.setIdProducto(updatedVentas.getIdProducto());
            ventas.setIdVendedor(updatedVentas.getIdVendedor());

            // Guardar las ventas actualizadas en la base de datos
            Ventas updated = ventasService.saveVentas(ventas);
            EntityModel<Ventas> resource = EntityModel.of(updated,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VentasController.class).getVentasById(id))
                            .withSelfRel());
            return ResponseEntity.ok(resource);
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

        for (Ventas venta : ventas) {
            // Convertir la fecha de SQL Date a LocalDate
            LocalDate fechaVenta = venta.getFecha().toLocalDate();

            // Verificar si la fecha de venta es la fecha actual
            if (fechaVenta.equals(fechaActual)) {
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

        for (Ventas venta : ventas) {
            // Convertir la fecha de SQL Date a LocalDate
            LocalDate fechaVenta = venta.getFecha().toLocalDate();

            // Verificar si la fecha de venta corresponde al mes actual
            if (fechaVenta.format(formatter).equals(fechaActual.format(formatter))) {
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
            // Convertir la fecha de SQL Date a LocalDate
            LocalDate fechaVenta = venta.getFecha().toLocalDate();

            // Verificar si la fecha de venta corresponde al año actual
            if (fechaVenta.getYear() == añoActual) {
                gananciasAnuales += venta.getTotal();
            }
        }
        return ResponseEntity.ok(gananciasAnuales);
    }

}
