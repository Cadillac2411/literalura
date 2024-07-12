package com.literalura.demo.service;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CatalogoEstadistica {


    private Scanner teclado = new Scanner(System.in);
    private LibroServicio libroServicio;
    private AutorServicio autorServicio;

    public CatalogoEstadistica(LibroServicio libroServicio, AutorServicio autorServicio) {
        this.libroServicio = libroServicio;
        this.autorServicio = autorServicio;
    }

    public void mostrarEstadisticas() {

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    -----------------
                    Seleccione una opcion del menu:
                    
                    Estatísticas de libros registrados:
                    1 - Total de libros registrados   
                    2 - Libros mas descargados
                    
                    Estadisticas de Autores:
                    3 - Total de autores registrados
                    4 - Promedio de libros por autor
                                        
                    0 - Salir
                    """;

            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1:
                        libroServicio.totalDeLibros();
                        break;
                    case 2:
                        libroServicio.librosMasDescargados();
                        break;
                    case 3:
                        autorServicio.totalDeAutores();
                        break;
                    case 4:
                        autorServicio.promedioDeLibrosPorAutor();
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opcion invalida, porfavor, intente de nuevo.");
                        break;

                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número válido.");
                teclado.nextLine();
            }
        }
    }
}
