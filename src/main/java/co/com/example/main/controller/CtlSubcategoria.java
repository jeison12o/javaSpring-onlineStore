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

import co.com.example.main.domain.Categoria;
import co.com.example.main.domain.Producto;
import co.com.example.main.domain.Subcategoria;
import co.com.example.main.domain.Usuario;
import co.com.example.main.repository.RepoCategoria;
import co.com.example.main.repository.RepoSubcategoria;
import co.com.example.main.repository.RepoUsuario;
import co.com.example.main.security.util.UserAutenticado;

@Controller
public class CtlSubcategoria {

	@Autowired
	private RepoSubcategoria repoSubcategoria;

	@Autowired
	private RepoCategoria repoCategoria;

	@Autowired
	private RepoUsuario repoUsuario;
	@Autowired
	private UserAutenticado userAutenticado;
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/registroSubcategoria/{idVendedor}/pag/{page}")
	public String registroSubcategoria(Model model, @PathVariable("idVendedor") int idVendedor,
			@PathVariable("page") int page) {
		UserDetails user1 = userAutenticado.getAuth();
		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
		if (user.getId()!=idVendedor) {
			return "denegado";
		}
		model.addAttribute("subcategoria", new Subcategoria());
		model.addAttribute("idVendedor", idVendedor);
		model.addAttribute("listaSubcategorias", this.repoSubcategoria.findAll(PageRequest.of(page, 2)));
		model.addAttribute("listaCategorias", repoCategoria.findAll());
		model.addAttribute("usuario", user);
		model.addAttribute("producto", new Producto());
		return "registroSubcategoria";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/registroSubcategoria/{idVendedor}")
	public String registroSubcategoria(Model model, @PathVariable int idVendedor) {
		UserDetails user1 = userAutenticado.getAuth();
		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
		if (user.getId()!=idVendedor) {
			return "denegado";
		}
		model.addAttribute("subcategoria", new Subcategoria());
		model.addAttribute("idVendedor", idVendedor);
		model.addAttribute("listaSubcategorias", this.repoSubcategoria.findAll(PageRequest.of(0, 2)));
		model.addAttribute("listaCategorias", repoCategoria.findAll());
		model.addAttribute("usuario", user);
		model.addAttribute("producto", new Producto());
		return "registroSubcategoria";
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/guardarSubcategoria/{idVendedor}")
	public String guardarSubcategoria(@Valid Subcategoria s, BindingResult result, Model model,
			@PathVariable int idVendedor) {
		UserDetails user1 = userAutenticado.getAuth();
		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
		if (user.getId()!=idVendedor) {
			return "denegado";
		}
		if (s.getIdCategoria() > 0) {
			Categoria c = repoCategoria.findById(s.getIdCategoria());
			s.setCategoria(c);
			s.setUsuario(repoUsuario.findById(idVendedor));
			if (result.hasErrors()) {
				model.addAttribute("subcategoria", s);
				model.addAttribute("idVendedor", idVendedor);
				model.addAttribute("listaSubcategorias", this.repoSubcategoria.findAll(PageRequest.of(0, 2)));
				model.addAttribute("listaCategorias", repoCategoria.findAll());
				model.addAttribute("usuario", user);
				model.addAttribute("producto", new Producto());
				return "registroSubcategoria";
			}
			if (repoSubcategoria.findByNombre(s.getNombre()) != null) {
				model.addAttribute("subcategoria", s);
				model.addAttribute("idVendedor", idVendedor);
				model.addAttribute("listaSubcategorias", this.repoSubcategoria.findAll(PageRequest.of(0, 2)));
				model.addAttribute("listaCategorias", repoCategoria.findAll());
				model.addAttribute("usuario", user);
				model.addAttribute("producto", new Producto());
				model.addAttribute("error", "Este nombre ya está en uso");
				return "registroSubcategoria";
			}
			repoSubcategoria.save(s);
			model.addAttribute("subcategoria", new Subcategoria());
			model.addAttribute("listaSubcategorias", repoSubcategoria.findAll());
			model.addAttribute("listaCategorias", repoCategoria.findAll());
			model.addAttribute("idVendedor", idVendedor);
			model.addAttribute("producto", new Producto());
			return "redirect:/registroSubcategoria/" + idVendedor;
		} else {
			model.addAttribute("subcategoria", new Subcategoria());
			model.addAttribute("listaSubcategorias", repoSubcategoria.findAll());
			model.addAttribute("listaCategorias", repoCategoria.findAll());
			model.addAttribute("idVendedor", idVendedor);
			model.addAttribute("producto", new Producto());
			return "redirect:/registroSubcategoria/" + idVendedor;
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/editarSubcategoria/{id}")
	public String editarSubcategoria(Model model, @PathVariable int id) {
		Subcategoria s = repoSubcategoria.findById(id);
		model.addAttribute("subcategoria", s);
		model.addAttribute("listaCategorias", repoCategoria.findAll());
		model.addAttribute("usuario", s.getUsuario());
		model.addAttribute("idSubcategoria", s.getId());
		model.addAttribute("producto", new Producto());
		return "editarSubcategoria";
	}
	

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/modificarSubcategoria/{id}")
	public String modificarSubcategoria(@Valid Subcategoria s, BindingResult result, Model model,
			@PathVariable int id) {
		int idVendedor = repoSubcategoria.findById(id).getUsuario().getId();
		Categoria c = repoCategoria.findById(s.getIdCategoria());
		s.setCategoria(c);
		s.setId(id);
		s.setUsuario(repoUsuario.findById(idVendedor));
		if (result.hasErrors()) {
			model.addAttribute("subcategoria", s);
			model.addAttribute("listaCategorias", repoCategoria.findAll());
			model.addAttribute("usuario", s.getUsuario());
			model.addAttribute("idSubcategoria", s.getId());
			model.addAttribute("producto", new Producto());
			return "editarSubcategoria";
		}
		if (repoSubcategoria.findByNombre(s.getNombre()) != null) {
			if (repoSubcategoria.findByNombre(s.getNombre()).getId() != id) {
				model.addAttribute("error", "Intente con otro nombre");
				model.addAttribute("subcategoria", s);
				model.addAttribute("listaCategorias", repoCategoria.findAll());
				model.addAttribute("usuario", s.getUsuario());
				model.addAttribute("idSubcategoria", s.getId());
				model.addAttribute("producto", new Producto());
				return "editarSubcategoria";
			}
		}
		
		repoSubcategoria.save(s);
		model.addAttribute("subcategoria", new Subcategoria());
		model.addAttribute("listaSubcategorias", repoSubcategoria.findAll());
		model.addAttribute("listaCategorias", repoCategoria.findAll());
		model.addAttribute("idVendedor", idVendedor);
		model.addAttribute("producto", new Producto());
		return "redirect:/registroSubcategoria/" + idVendedor;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/eliminarSubcategoria/{id}")
	public String eliminarSubcategoria(Model model, @PathVariable int id) {
		int idVendedor = repoSubcategoria.findById(id).getUsuario().getId();
		repoSubcategoria.deleteById(id);
		model.addAttribute("subcategoria", new Subcategoria());
		model.addAttribute("listaSubcategorias", repoSubcategoria.findAll());
		model.addAttribute("listaCategorias", repoCategoria.findAll());
		model.addAttribute("idVendedor", idVendedor);
		model.addAttribute("producto", new Producto());
		return "redirect:/registroSubcategoria/" + idVendedor;
	}
	
}
