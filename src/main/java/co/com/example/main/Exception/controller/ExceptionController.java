package co.com.example.main.Exception.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionController {
	@RequestMapping("/accesoDenegado")
	public String denegado() {
		return "denegado";
	}
}
