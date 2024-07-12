package com.literalura.demo.service;

import com.literalura.demo.model.Autor;
import com.literalura.demo.model.Libro;

import com.literalura.demo.model.DatosLibros;
import com.literalura.demo.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class LibroServicio {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private final String url = "https://gutendex.com/books/?search=";
    private LibroRepository repositorioLibro;
    private List<Libro> libros = new ArrayList<>();
    static Optional<Libro> libroBuscado;

    @Autowired
    public LibroServicio(LibroRepository repositorioLibro) {
        this.repositorioLibro = repositorioLibro;
    }

    public void buscarLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar: ");
        var nombreLibro = teclado.nextLine();
        buscarDatosLibro(nombreLibro);
    }
    private void buscarLibro(String nombreLibro) {
        if (libroBuscado.isPresent()) {
            return;
        }

        var json = consumo.obtenerData(url + nombreLibro.replace(" ", "+"));

        com.literalura.demo.model.DataConverter conversor = new com.literalura.demo.model.DataConverter();
        DatosLibros datosLibros = conversor.obterData(json, DatosLibros.class);

        if (datosLibros != null && datosLibros.results() != null && !datosLibros.results().isEmpty()) {
            Libro primerLibro = datosLibros.results().get(0);

            if (primerLibro.getLanguages() != null && !primerLibro.getLanguages().isEmpty()) {
                List<String> idiomas = new ArrayList<>();
                idiomas.add(primerLibro.getLanguages().get(0));
                primerLibro.setLanguages(idiomas);
            }

            if(primerLibro.getAuthors() != null && !primerLibro.getAuthors().isEmpty()) {
                List<Autor> authors = new ArrayList<>();
                authors.add(primerLibro.getAuthors().get(0));
                primerLibro.setAuthors(authors);
            }

            for (Autor author : primerLibro.getAuthors()) {
                author.getBooks().add(primerLibro);
            }

            repositorioLibro.save(primerLibro);

            System.out.println(primerLibro);
        } else {
            System.out.println("No se encontro ningun libro :(");
        }
    }

    public void buscarDatosLibro(String nombreLibro) {
        libroBuscado = repositorioLibro.findByTitleContainingIgnoreCase(nombreLibro);
        if (libroBuscado.isPresent()) {
            Libro bookFound = libroBuscado.get();
            System.out.println(bookFound);
        } else {
            buscarLibro(nombreLibro);
        }
    }
    public void mostrarLibrosRegistrados() {
        List<Libro> libros = repositorioLibro.findAll();

        if (libros.isEmpty()) {
            System.out.println("No se encuentran libros registrados en la base de datos.");
        } else {
            libros.stream()
                    .sorted(Comparator.comparing(Libro::getAuthorNames))
                    .forEach(System.out::println);
        }
    }

    public void mostrarLibrosPorIdioma() {
        var menu = """ 
                Selecciona un idioma:
                es - espanol
                en - ingles
                fr - frances
                pt - portugues
                """;
        System.out.println(menu);
        var idiomaLibro = teclado.nextLine();
        libros = repositorioLibro.findByLanguage(idiomaLibro.toLowerCase());
        if (!libros.isEmpty()) {
            libros.forEach(System.out::println);
        } else {
            System.out.println("No se encontro ningun libro en la base de datos en ese idioma.");
        }
    }

    public void top10LibrosDescargados() {
        libros = repositorioLibro.findTop10ByOrderByDownloadsDesc();
        libros.forEach(System.out::println);
    }

    public void totalDeLibros() {
        libros = repositorioLibro.findAll().stream()
                .sorted(Comparator.comparing(Libro::getTitle))
                .collect(Collectors.toList());
        if (!libros.isEmpty()) {
            System.out.println("Total de libros registrados: " + libros.size());
        } else {
            System.out.println("Ningun libro registrado.");
        }
    }

    public void librosMasDescargados() {
        libros = repositorioLibro.findAll().stream()
                .sorted(Comparator.comparing(Libro::getDownloads).reversed())
                .collect(Collectors.toList());
        libros.forEach(System.out::println);
    }
}




