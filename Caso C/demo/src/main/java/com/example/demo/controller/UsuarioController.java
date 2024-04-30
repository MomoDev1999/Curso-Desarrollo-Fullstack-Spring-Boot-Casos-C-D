package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<EntityModel<Usuario>>> getAllUsuarios() {
        List<EntityModel<Usuario>> usuarios = usuarioService.getAllUsuarios().stream()
                .map(usuario -> EntityModel.of(usuario,
                        Link.of("/usuarios/" + usuario.getUsername()).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{username}")
    public ResponseEntity<EntityModel<Usuario>> getUsuarioByUsername(@PathVariable String username) {
        Optional<Usuario> usuario = usuarioService.getUsuarioByUsername(username);
        return usuario.map(user -> EntityModel.of(user,
                Link.of("/usuarios/" + username).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Usuario>> createUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.saveUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(nuevoUsuario,
                Link.of("/usuarios/" + nuevoUsuario.getUsername()).withSelfRel()));
    }

    @PutMapping("/{username}")
    public ResponseEntity<EntityModel<Usuario>> updateUsuario(@PathVariable String username,
            @RequestBody Usuario updatedUsuario) {
        Optional<Usuario> existingUsuario = usuarioService.getUsuarioByUsername(username);

        if (existingUsuario.isPresent()) {
            Usuario usuario = existingUsuario.get();
            // Actualizar los campos del usuario con los datos recibidos
            usuario.setNombre(updatedUsuario.getNombre());
            usuario.setApellido(updatedUsuario.getApellido());
            usuario.setEdad(updatedUsuario.getEdad());

            // Guardar el usuario actualizado en la base de datos
            Usuario updated = usuarioService.saveUsuario(usuario);
            return ResponseEntity.ok(EntityModel.of(updated,
                    Link.of("/usuarios/" + updated.getUsername()).withSelfRel()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable String username) {
        boolean deleted = usuarioService.deleteUsuario(username);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
