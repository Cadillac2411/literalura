package com.literalura.demo.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "books")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonAlias("title")private String title;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "book_languages", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "language")
    @JsonAlias("languages") private List<String> languages;
    @JsonAlias("download_count") private int downloads;

    @ManyToMany(mappedBy = "books", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonAlias("authors") private List<Autor> authors = new ArrayList<>();

    public Libro() {
        this.languages = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Autor> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Autor> authors) {
        this.authors = authors;
    }

    public void addLanguage(String language) {
        this.languages.add(language);
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {

        if (languages != null && !languages.isEmpty()) {
            this.languages.clear();
            this.languages.add(languages.get(0));
        }
    }
    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public String getAuthorNames() {
        StringBuilder sb = new StringBuilder();
        if (authors != null) {
            for (Autor author : authors) {
                sb.append(author.getName()).append(", ");
            }

            if (sb.length() > 2) {
                sb.setLength(sb.length() - 2);
            }
        }
        return sb.toString();
    }

    public String getFirstLanguage() {
        if (languages != null && !languages.isEmpty()) {
            return languages.get(0);
        } return "Lengua indefinida";
    }

    @Override
    public String toString() {
        String authorsNames = authors.stream()
                .map(Autor::getName)
                .collect(Collectors.joining(", "));

        return  "Libro: " +
                "\n -Título= " + title +
                "\n -Autor= " + authorsNames +
                "\n -Idioma= " + (languages.isEmpty() ? "Lengua indefinida" : languages.get(0)) +
                "\n -Número de descargas= " + downloads +
                "\n---------------\n";
    }
}