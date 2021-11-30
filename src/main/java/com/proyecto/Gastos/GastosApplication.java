package com.proyecto.Gastos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GastosApplication {
	private static Logger log = LogManager.getLogger(GastosApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GastosApplication.class, args);
		log.info("Servicio Iniciado");
	}

}
