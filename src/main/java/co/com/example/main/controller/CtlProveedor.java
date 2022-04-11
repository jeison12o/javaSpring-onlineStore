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
import org.springframework.web.bind.annotation.RequestParam;

import co.com.example.main.domain.Producto;
import co.com.example.main.domain.Proveedor;
import co.com.example.main.domain.Usuario;
import co.com.example.main.repository.RepoProveedor;
import co.com.example.main.repository.RepoUsuario;
import co.com.example.main.security.util.UserAutenticado;

@Controller
public class CtlProveedor {

	@Autowired
	private RepoProveedor repoProveedor;
	@Autowired
	private UserAutenticado userAutenticado;
	@Autowired
	private RepoUsuario repoUsuario;
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/registroProveedor/{idVendedor}")
	public String registroProveedor(Model model, @PathVariable int idVendedor,
			@RequestParam(defaultValue = "0") int page) {
		UserDetails user1 = userAutenticado.getAuth();
		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
		if (user.getId()!=idVendedor) {
			return "denegado";
		}
		model.addAttribute("proveedor", new Proveedor());
		model.addAttribute("idVendedor", idVendedor);
		model.addAttribute("usuario", user);
		model.addAttribute("listaProveedores", this.repoProveedor.findAll(PageRequest.of(page, 2)));
		model.addAttribute("producto", new Producto());
		return "registroProveedor";
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/registroProveedor/{idVendedor}/pag/{page}")
	public String pagRegistroProveedor(Model model, @PathVariable int idVendedor, @PathVariable("page") int page) {
		UserDetails user1 = userAutenticado.getAuth();
		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
		if (user.getId()!=idVendedor) {
			return "denegado";
		}
		model.addAttribute("proveedor", new Proveedor());
		model.addAttribute("idVendedor", idVendedor);
		model.addAttribute("usuario", repoUsuario.findById(idVendedor));
		model.addAttribute("listaProveedores", this.repoProveedor.findAll(PageRequest.of(page, 2)));
		model.addAttribute("producto", new Producto());
		return "registroProveedor";
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/guardarProveedor/{idVendedor}")
	public String guardarProveedor(@Valid Proveedor proveedor, BindingResult result, Model model,
			@PathVariable int idVendedor) {
		UserDetails user1 = userAutenticado.getAuth();
		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
		if (user.getId()!=idVendedor) {
			return "denegado";
		}
		proveedor.setUsuario(repoUsuario.findById(idVendedor));
		if (result.hasErrors()) {
			model.addAttribute("proveedor", proveedor);
			model.addAttribute("idVendedor", idVendedor);
			model.addAttribute("usuario", user);
			model.addAttribute("listaProveedores", this.repoProveedor.findAll(PageRequest.of(0, 2)));
			model.addAttribute("producto", new Producto());
			return "registroProveedor";
		}
		if (repoProveedor.findByNombre(proveedor.getNombre()) != null) {
			model.addAttribute("proveedor", proveedor);
			model.addAttribute("idVendedor", idVendedor);
			model.addAttribute("usuario", repoUsuario.findById(idVendedor));
			model.addAttribute("listaProveedores", this.repoProveedor.findAll(PageRequest.of(0, 2)));
			model.addAttribute("producto", new Producto());
			model.addAttribute("error", "Intente con otro nombre");
			return "registroProveedor";
		}
		repoProveedor.save(proveedor);
		model.addAttribute("proveedor", new Proveedor());
		model.addAttribute("listaProveedores", repoProveedor.findAll());
		model.addAttribute("idVendedor", idVendedor);
		model.addAttribute("producto", new Producto());
		return "redirect:/registroProveedor/" + idVendedor;
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/editarProveedor/{id}")
	public String editarProveedor(@PathVariable int id, Model model) {

		Proveedor p = repoProveedor.findById(id);
		model.addAttribute("proveedor", p);
		model.addAttribute("usuario", p.getUsuario());
		model.addAttribute("producto", new Producto());
		return "editarProveedor";
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/modificarProveedor/{id}")
	public String modificarProveedor(@Valid Proveedor proveedor, BindingResult result, Model model,
			@PathVariable int id) {
		int idVendedor = repoProveedor.findById(id).getUsuario().getId();
		proveedor.setId(id);
		proveedor.setUsuario(repoUsuario.findById(idVendedor));
		if (result.hasErrors()) {
			model.addAttribute("proveedor", proveedor);
			model.addAttribute("usuario", repoProveedor.findById(id).getUsuario());
			model.addAttribute("producto", new Producto());
			return "editarProveedor";
		}
		if (repoProveedor.findByNombre(proveedor.getNombre()) != null) {
			if (repoProveedor.findByNombre(proveedor.getNombre()).getId() != id) {
				model.addAttribute("proveedor", proveedor);
				model.addAttribute("usuario", repoProveedor.findById(id).getUsuario());
				model.addAttribute("producto", new Producto());
				model.addAttribute("error", "Ya existe un proveedor con este nombre");
				return "editarProveedor";
			}
		}
		repoProveedor.save(proveedor);
		model.addAttribute("proveedor", new Proveedor());
		model.addAttribute("listaProveedores", repoProveedor.findAll());
		model.addAttribute("idVendedor", idVendedor);
		model.addAttribute("producto", new Producto());
		return "redirect:/registroProveedor/" + idVendedor;
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/eliminarProveedor/{id}")
	public String eliminarProveedor(Model model, @PathVariable int id) {
		int idVendedor = repoProveedor.findById(id).getUsuario().getId();
		repoProveedor.deleteById(id);
		model.addAttribute("proveedor", new Proveedor());
		model.addAttribute("listaProveedores", repoProveedor.findAll());
		model.addAttribute("idVendedor", idVendedor);
		model.addAttribute("producto", new Producto());
		return "redirect:/registroProveedor/" + idVendedor;
	}

}
