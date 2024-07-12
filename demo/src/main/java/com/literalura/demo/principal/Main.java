package com.literalura.demo.principal;

import com.literalura.demo.service.AutorServicio;
import com.literalura.demo.service.CatalogoEstadistica;
import com.literalura.demo.service.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Main {

    private Scanner teclado = new Scanner(System.in);
    private LibroServicio libroServicio;
    private AutorServicio autorServicio;
    private CatalogoEstadistica catalogoEstadistica;

    @Autowired
    public Main(LibroServicio libroServicio, AutorServicio autorServicio, CatalogoEstadistica catalogoEstadistica) {
        this.libroServicio = libroServicio;
        this.autorServicio = autorServicio;
        this.catalogoEstadistica = catalogoEstadistica;
        this.teclado = new Scanner(System.in);
    }

    public void menu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    -----------------
                    Bienvenido, porfavor, seleccione una opcion del menu:
                    1 - Buscar libro por titulo
                    2 - Mostrar libros registrados
                    3 - Mostrar autores registrados
                    4 - Mostrar autores vivos en un anio determinado
                    5 - Mostrar libros en un determinado idioma
                    6 - Mostrar 10 libros mas descargados
                    7 - Mostrar autores por orden alfabetico
                    8 - Mostrar estadísticas
                    

                    0 - Salir
                    """;

            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1:
                        libroServicio.buscarLibro();
                        break;
                    case 2:
                        libroServicio.mostrarLibrosRegistrados();
                        break;
                    case 3:
                        autorServicio.mostrarAutoresRegistrados();
                        break;
                    case 4:
                        autorServicio.buscarAutoresPorAnio();
                        break;
                    case 5:
                        libroServicio.mostrarLibrosPorIdioma();
                        break;
                    case 6:
                        libroServicio.top10LibrosDescargados();
                        break;
                    case 7:
                        autorServicio.buscarAutoresPorNombre();
                        break;
                    case 8:
                        catalogoEstadistica.mostrarEstadisticas();
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida, porfavor, intente de nuevo.");
                        break;

                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Ingrese un número válido.");
                teclado.nextLine();
            }

        }
    }
}