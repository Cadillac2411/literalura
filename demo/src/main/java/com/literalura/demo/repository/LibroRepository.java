package com.literalura.demo.repository;

import com.literalura.demo.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTitleContainingIgnoreCase(String nomeLivro);

    @Query("SELECT b FROM Libro b WHERE LOWER(:language) MEMBER OF b.languages")
    List<Libro> findByLanguage(@Param("language") String language);

    List<Libro> findTop10ByOrderByDownloadsDesc();
}
