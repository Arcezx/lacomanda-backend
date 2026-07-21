package com.lacomanda.backend.repository;

import com.lacomanda.backend.entity.Alergeno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlergenoRepository extends JpaRepository<Alergeno, Long> {
}