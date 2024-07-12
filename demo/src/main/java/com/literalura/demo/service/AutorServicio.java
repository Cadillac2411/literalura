package com.literalura.demo.service;
import com.literalura.demo.model.Autor;
import com.literalura.demo.repository.AutorRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AutorServicio {

    private AutorRepository repositorioAutor;
    private Scanner teclado = new Scanner(System.in);
    private List<Autor> autores = new ArrayList<>();

    public AutorServicio(AutorRepository repositorioAutor) {
        this.repositorioAutor = repositorioAutor;
    }

    public void mostrarAutoresRegistrados() {
        autores = repositorioAutor.findAll().stream()
                .sorted(Comparator.comparing(Autor::getName))
                .collect(Collectors.toList());
        if(!autores.isEmpty()) {
            autores.forEach(System.out::println);
        } else {
            System.out.println("No hay autores registrados");
        }
    }

    public void buscarAutoresPorAnio() {
        System.out.println("Ingrese un anio YYYY para consultar los autores vivos en dicho anio: ");
        var anio = teclado.nextInt();
        List<Autor> autores = repositorioAutor.autoresPorAnio(anio);
        if(!autores.isEmpty()) {
            autores.forEach(System.out::println);
        } else {
            System.out.println("No se encontraron autores para dicho anio.");
        }
    }

    public void buscarAutoresPorNombre() {
        System.out.println("Ingrese el nombre del autor que desea buscar: ");
        var autorNombre = teclado.nextLine();
        autores = repositorioAutor.AutorPorNombre(autorNombre);
        if(!autores.isEmpty()) {
            autores.forEach(System.out::println);
        } else {
            System.out.println("No se encontro ningun autor !");
        }
    }

    public void totalDeAutores() {
        autores = repositorioAutor.findAll().stream()
                .sorted(Comparator.comparing(Autor::getName))
                .collect(Collectors.toList());
        if (!autores.isEmpty()) {
            System.out.println("Total de autores registrados: " + autores.size());
        } else {
            System.out.println("No hay autores registrados.");
        }
    }

    public void promedioDeLibrosPorAutor() {
        DoubleSummaryStatistics stats = repositorioAutor.findAll().stream()
                .mapToDouble(a -> a.getBooks().size())
                .summaryStatistics();
        double media = stats.getAverage();
        System.out.println("Promedio de libros por autor: " + media);
    }
}
