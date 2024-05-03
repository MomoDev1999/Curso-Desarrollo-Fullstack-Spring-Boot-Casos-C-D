package com.example.demo.controller;

import com.example.demo.model.Producto;
import com.example.demo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Hateoas
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public CollectionModel<EntityModel<Producto>> getAllProductos() {
        List<Producto> productos = productoService.getAllProductos();
        List<EntityModel<Producto>> productoResource = productos.stream()
                .map(producto -> EntityModel.of(producto,
                        WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProductoById(producto.getId()))
                                .withSelfRel()))
                .collect(Collectors.toList());
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllProductos());
        CollectionModel<EntityModel<Producto>> resources = CollectionModel.of(productoResource,
                linkTo.withRel("products"));
        return resources;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Producto>> getProductoById(@PathVariable int id) {
        Optional<Producto> productoOptional = productoService.getProductoById(id);
        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            EntityModel<Producto> resource = EntityModel.of(producto,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProductoById(id))
                            .withSelfRel());
            return ResponseEntity.ok(resource);
        } else {
            throw new ProductoNotFoundException("Producto no encontrado con id: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Producto>> createProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.saveProducto(producto);

        EntityModel<Producto> resource = EntityModel.of(nuevoProducto,
                linkTo(methodOn(ProductoController.class).getProductoById(nuevoProducto.getId())).withSelfRel());

        return ResponseEntity.created(resource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Producto>> updateProducto(@PathVariable int id,
            @RequestBody Producto updatedProducto) {
        Optional<Producto> existingProducto = productoService.getProductoById(id);

        if (existingProducto.isPresent()) {
            Producto producto = existingProducto.get();
            // Actualizar los campos del producto con los datos recibidos
            producto.setNombre(updatedProducto.getNombre());
            producto.setPrecio(updatedProducto.getPrecio());

            // Guardar el producto actualizado en la base de datos
            Producto updated = productoService.saveProducto(producto);

            EntityModel<Producto> resource = EntityModel.of(updated,
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProductoById(updated.getId()))
                            .withSelfRel());

            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable int id) {
        boolean deleted = productoService.deleteProducto(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
