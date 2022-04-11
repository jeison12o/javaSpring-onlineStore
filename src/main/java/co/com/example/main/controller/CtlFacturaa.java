package co.com.example.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import co.com.example.main.domain.DetalleFactura;
import co.com.example.main.domain.Facturaa;
import co.com.example.main.domain.Producto;
import co.com.example.main.domain.Usuario;
import co.com.example.main.repository.RepoDetalleFactura;
import co.com.example.main.repository.RepoFacturaa;
import co.com.example.main.repository.RepoUsuario;
import co.com.example.main.security.util.UserAutenticado;

@Controller
public class CtlFacturaa {

	@Autowired
	private RepoFacturaa repoFactura;

	@Autowired
	private RepoUsuario repoUsuario;

	@Autowired
	private RepoDetalleFactura repoDetalleFactura;
	
	@Autowired
	private UserAutenticado userAutenticado;
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/facturaArchivoPlano/{idUsuario}/{idFactura}")
	public String archivoPlano(Model model, @PathVariable("idUsuario") int idUsuario, @PathVariable("idFactura") int idFactura) {
		UserDetails user1 = userAutenticado.getAuth();
		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
		if (user.getId()!=idUsuario) {
			return "denegado";
		}
		Facturaa f = this.repoFactura.findById(idFactura);
		List<DetalleFactura> lf = this.repoDetalleFactura.findByFactura(f);
		model.addAttribute("factura", f);
		model.addAttribute("listaDetalles", lf);
		model.addAttribute("comprador", f.getComprador());
		return "FacturaArchivoPlano";
	}
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/visualizarFacturas/{idUsuario}")
	public String visualizarFacturas(Model model, @PathVariable("idUsuario") int idUsuario) {
		UserDetails user1 = userAutenticado.getAuth();
		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
		if (user.getId()!=idUsuario) {
			return "denegado";
		}
		model.addAttribute("producto", new Producto());
		model.addAttribute("listaFacturas", this.repoFactura.findByComprador(PageRequest.of(0, 5), user));
		model.addAttribute("usuario", user);
		return "visualizarFacturas";
	}
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/visualizarFacturas/{idUsuario}/page/{page}")
	public String visualizarFacturas(Model model, @PathVariable("idUsuario") int idUsuario, @PathVariable("page") int page) {
		UserDetails user1 = userAutenticado.getAuth();
		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
		if (user.getId()!=idUsuario) {
			return "denegado";
		}
		model.addAttribute("producto", new Producto());
		model.addAttribute("listaFacturas", this.repoFactura.findByComprador(PageRequest.of(page, 5), user));
		model.addAttribute("usuario", user);
		return "visualizarFacturas";
	}
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/visualizarDetalle/{idFactura}/{idUsuario}")
	public String visualizarDetalleFactura(Model model, @PathVariable("idUsuario") int idUsuario,
			@PathVariable("idFactura") int idFactura) {
		UserDetails user1 = userAutenticado.getAuth();
		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
		if (user.getId()!=idUsuario) {
			return "denegado";
		}
		Facturaa f = this.repoFactura.findById(idFactura);
		model.addAttribute("producto", new Producto());
		model.addAttribute("listaDetalles", this.repoDetalleFactura.findByFactura(PageRequest.of(0, 6), f));
		model.addAttribute("usuario", this.repoUsuario.findById(idUsuario));
		model.addAttribute("factura", f);
		return "detalleFactura";
	}
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/visualizarDetalle/{idFactura}/{idUsuario}/{page}")
	public String pagVisualizarDetalleFactura(Model model, @PathVariable("idUsuario") int idUsuario,
			@PathVariable("idFactura") int idFactura, @PathVariable("page") int page) {
		UserDetails user1 = userAutenticado.getAuth();
		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
		if (user.getId()!=idUsuario) {
			return "denegado";
		}
		Facturaa f = this.repoFactura.findById(idFactura);
		model.addAttribute("producto", new Producto());
		model.addAttribute("listaDetalles", this.repoDetalleFactura.findByFactura(PageRequest.of(page, 6), f));
		model.addAttribute("usuario", this.repoUsuario.findById(idUsuario));
		model.addAttribute("factura", f);
		return "detalleFactura";
	}

}
