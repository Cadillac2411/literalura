package com.literalura.demo;

import com.literalura.demo.principal.Main;
import com.literalura.demo.repository.AutorRepository;
import com.literalura.demo.repository.LibroRepository;
import com.literalura.demo.service.CatalogoEstadistica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	private AutorRepository autorRepository;

	@Autowired
	private CatalogoEstadistica catalogoEstadistica;

	@Autowired
	private Main main;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		main.menu();
	}
}
