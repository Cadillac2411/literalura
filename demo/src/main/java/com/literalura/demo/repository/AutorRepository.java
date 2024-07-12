package com.literalura.demo.repository;

import com.literalura.demo.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a " + "WHERE (a.death_year IS NULL OR a.death_year > :date) " + "AND a.birth_year <= :date")
    List<Autor> autoresPorAnio(@Param("date") int date);

    @Query("SELECT a FROM Autor a WHERE lower(a.name) ILIKE (concat('%', :authorName, '%'))")
    List<Autor> AutorPorNombre(String authorName);
}