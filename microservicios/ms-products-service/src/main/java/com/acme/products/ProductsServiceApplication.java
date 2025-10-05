package com.acme.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Clase principal para la aplicación de servicio de productos.
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class ProductsServiceApplication {

	/**
	 * Método principal para iniciar la aplicación.
	 * 
	 * @param args Argumentos de línea de comandos.
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProductsServiceApplication.class, args);
	}

}
