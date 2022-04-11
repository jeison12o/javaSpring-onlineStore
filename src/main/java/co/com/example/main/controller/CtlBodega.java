package co.com.example.main.controller;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import co.com.example.main.domain.Bodega;
import co.com.example.main.domain.Producto;
import co.com.example.main.domain.Usuario;
import co.com.example.main.repository.RepoBodega;
import co.com.example.main.repository.RepoUsuario;
import co.com.example.main.security.util.UserAutenticado;


@Controller
public class CtlBodega {

	@Autowired
	private RepoBodega repoBodega;
@Autowired
private UserAutenticado userAutenticado;
	@Autowired
	private RepoUsuario repoUsuario;

	public boolean existeBodega(String nombre) {
		if (repoBodega.findByNombre(nombre) != null) {
			return true;
		}
		return false;
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("registroBodega/{idVendedor}/pag/{page}")
	public String registroBodegaPag(Model model, @PathVariable("idVendedor") int idVendedor,
			@PathVariable("page") int page) {
		UserDetails user1 = userAutenticado.getAuth();

		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
	
		if (user.getId()!=idVendedor) {
			return "denegado";
		}
		model.addAttribute("idVendedor", idVendedor);
		model.addAttribute("bodega", new Bodega());
		model.addAttribute("listaBodegas", this.repoBodega.findAll(PageRequest.of(page, 3)));
		model.addAttribute("usuario", user);
		model.addAttribute("producto", new Producto());
		return "registroBodega";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/registroBodega/{idVendedor}")
	public String registroBodega(Model model, @PathVariable int idVendedor) {
		UserDetails user1 = userAutenticado.getAuth();

		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
	
		if (user.getId()!=idVendedor) {
			return "denegado";
					
		}
		model.addAttribute("idVendedor", idVendedor);
		model.addAttribute("bodega", new Bodega());
		model.addAttribute("listaBodegas", this.repoBodega.findAll(PageRequest.of(0, 3)));
		model.addAttribute("usuario", user);
		model.addAttribute("producto", new Producto());
		return "registroBodega";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/guardarBodega/{idVendedor}")
	public String guardarBodega(@Valid Bodega b, BindingResult result, Model model, @PathVariable int idVendedor) {
		UserDetails user1 = userAutenticado.getAuth();

		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
	
		if (user.getId()!=idVendedor) {
			return "denegado";			
		}
		
		b.setUsuario(repoUsuario.findById(idVendedor));
		b.setEspacioDisponible(b.getCapacidad());
		model.addAttribute("idVendedor", idVendedor);
		model.addAttribute("listaBodegas", this.repoBodega.findAll(PageRequest.of(0, 3)));
		model.addAttribute("producto", new Producto());
		model.addAttribute("bodega", b);
		model.addAttribute("usuario", user);
		if (existeBodega(b.getNombre())) {
			model.addAttribute("error", "El nombre de las bodegas es irrepetible");
			return "registroBodega";
		}
		if (result.hasErrors()) {
			return "registroBodega";
		} else {
			repoBodega.save(b);
			return "redirect:/registroBodega/" + idVendedor;
		}
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/editarBodega/{id}")
	public String editarBodega(Model model, @PathVariable int id) {
		
		Bodega b = repoBodega.findById(id);
		model.addAttribute("bodega", b);
		model.addAttribute("usuario", b.getUsuario());
		model.addAttribute("producto", new Producto());
		return "editarBodega";
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/modificarBodega/{id}")
	public String modificarBodega(@Valid Bodega b, BindingResult result, @PathVariable int id, Model model) {
		
		Bodega bodAux = repoBodega.findById(id);

		int idVendedor = bodAux.getUsuario().getId();

		b.setId(id);
		b.setUsuario(repoUsuario.findById(idVendedor));
		if (repoBodega.findAllByNombre(b.getNombre()).size() >= 1) {
			if (repoBodega.findByNombre(b.getNombre()).getId() != bodAux.getId()) {
				model.addAttribute("error", "Ya hay otra bodega con este nombre");
				model.addAttribute("bodega", b);
				model.addAttribute("listaBodegas", repoBodega.findAll());
				model.addAttribute("idVendedor", idVendedor);
				model.addAttribute("usuario", bodAux.getUsuario());
				model.addAttribute("producto", new Producto());
				return "editarBodega";

			}
		}
		if (result.hasErrors()) {
			model.addAttribute("bodega", b);
			model.addAttribute("listaBodegas", repoBodega.findAll());
			model.addAttribute("idVendedor", idVendedor);
			model.addAttribute("producto", new Producto());
			model.addAttribute("usuario", bodAux.getUsuario());
			return "editarBodega";

		} else {
			bodAux = repoBodega.findById(b.getId());
			bodAux.setCapacidad(b.getCapacidad());
			bodAux.setNombre(b.getNombre());
			bodAux.setDireccion(b.getDireccion());
			repoBodega.save(bodAux);
			model.addAttribute("bodega", new Bodega());
			model.addAttribute("listaBodegas", repoBodega.findAll());
			model.addAttribute("idVendedor", idVendedor);
			model.addAttribute("producto", new Producto());
			return "redirect:/registroBodega/" + idVendedor;
		}
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/eliminarBodega/{id}")
	public String eliminarBodega(Model model, @PathVariable int id) {
		int idVendedor = repoBodega.findById(id).getUsuario().getId();
		model.addAttribute("idVendedor", idVendedor);
		repoBodega.deleteById(id);
		model.addAttribute("bodega", new Bodega());
		model.addAttribute("listaBodegas", repoBodega.findAll());
		model.addAttribute("producto", new Producto());
		return "redirect:/registroBodega/" + idVendedor;
	}
}
