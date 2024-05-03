package com.example.demo.controller;

import com.example.demo.model.Vendedor;
import com.example.demo.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vendedores")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    @GetMapping
    public CollectionModel<EntityModel<Vendedor>> getAllVendedores() {
        List<Vendedor> vendedores = vendedorService.getAllVendedores();
        List<EntityModel<Vendedor>> vendedorResources = vendedores.stream()
                .map(vendedor -> EntityModel.of(vendedor,
                        WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(VendedorController.class).getVendedorById(vendedor.getId()))
                                .withSelfRel()))
                .collect(Collectors.toList());
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(VendedorController.class).getAllVendedores());
        CollectionModel<EntityModel<Vendedor>> resources = CollectionModel.of(vendedorResources,
                linkTo.withRel("vendedores"));
        return resources;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Vendedor>> getVendedorById(@PathVariable int id) {
        Optional<Vendedor> vendedorOptional = vendedorService.getVendedorById(id);
        if (vendedorOptional.isPresent()) {
            Vendedor vendedor = vendedorOptional.get();
            EntityModel<Vendedor> resource = EntityModel.of(vendedor,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VendedorController.class).getVendedorById(id))
                            .withSelfRel());
            return ResponseEntity.ok(resource);
        } else {
            throw new VendedorNotFoundException("Vendedor no encontrado con id: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Vendedor>> createVendedor(@RequestBody Vendedor vendedor) {
        Vendedor nuevoVendedor = vendedorService.saveVendedor(vendedor);
        EntityModel<Vendedor> resource = EntityModel.of(nuevoVendedor,
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(VendedorController.class).getVendedorById(nuevoVendedor.getId()))
                        .withSelfRel());
        return ResponseEntity
                .created(WebMvcLinkBuilder.linkTo(VendedorController.class).slash(nuevoVendedor.getId()).toUri())
                .body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Vendedor>> updateVendedor(@PathVariable int id,
            @RequestBody Vendedor updatedVendedor) {
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
            EntityModel<Vendedor> resource = EntityModel.of(updated,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VendedorController.class).getVendedorById(id))
                            .withSelfRel());
            return ResponseEntity.ok(resource);
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
