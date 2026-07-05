package com.example.demo.repository;

import com.example.demo.model.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoeRepository extends JpaRepository<Shoe, String> {
}
