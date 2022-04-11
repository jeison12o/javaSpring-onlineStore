package co.com.example.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ProyectoIngSoftLApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(ProyectoIngSoftLApplication.class, args);
	}

}
