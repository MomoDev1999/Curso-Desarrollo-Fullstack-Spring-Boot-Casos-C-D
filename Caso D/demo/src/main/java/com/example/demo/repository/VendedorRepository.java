package com.example.demo.repository;

import com.example.demo.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor, Integer> {
}
