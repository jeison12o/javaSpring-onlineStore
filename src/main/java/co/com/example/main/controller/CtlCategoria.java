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
import co.com.example.main.domain.Usuario;
import co.com.example.main.repository.RepoCategoria;
import co.com.example.main.repository.RepoUsuario;
import co.com.example.main.security.util.UserAutenticado;

@Controller
public class CtlCategoria {
	
	@Autowired
	UserAutenticado userAutenticado;
	
	@Autowired
	private RepoCategoria repoCategoria;

	@Autowired
	private RepoUsuario repoUsuario;
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("registroCategoria/{idUsuario}/pag/{page}")
	public String registroCategoriaPag(Model model, @PathVariable("idUsuario") int idVendedor,
			@PathVariable("page") int page) {
		UserDetails user1 = userAutenticado.getAuth();

		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
	
		if (user.getId()!=idVendedor) {
			return "denegado";			
		}
		model.addAttribute("categoria", new Categoria());
		model.addAttribute("idVendedor", idVendedor);
		model.addAttribute("listaCategorias", this.repoCategoria.findAll(PageRequest.of(page, 2)));
		model.addAttribute("usuario", user);
		model.addAttribute("producto", new Producto());
		return "registroCategoria";
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/registroCategoria/{idVendedor}")
	public String registroCategoria(Model model, @PathVariable int idVendedor) {
		UserDetails user1 = userAutenticado.getAuth();

		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
	
		if (user.getId()!=idVendedor) {
			return "denegado";			
		}
		model.addAttribute("categoria", new Categoria());
		model.addAttribute("idVendedor", idVendedor);
		model.addAttribute("listaCategorias", this.repoCategoria.findAll(PageRequest.of(0, 2)));
		model.addAttribute("usuario", user);
		model.addAttribute("producto", new Producto());
		return "registroCategoria";
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/guardarCategoria/{idVendedor}")
	public String guardarCategoria(@Valid Categoria categoria, BindingResult result, Model model,
			@PathVariable int idVendedor) {
		UserDetails user1 = userAutenticado.getAuth();

		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
	
		if (user.getId()!=idVendedor) {
			return "denegado";
					
		}
		 categoria.setUsuario(repoUsuario.findById(idVendedor));
		if (result.hasErrors()) {
			model.addAttribute("categoria", categoria);
			model.addAttribute("idVendedor", idVendedor);
			model.addAttribute("listaCategorias", this.repoCategoria.findAll(PageRequest.of(0, 2)));
			model.addAttribute("usuario", user);
			model.addAttribute("producto", new Producto());
			return "registroCategoria";
		}
		if (existeCategoria(categoria.getNombre())) {

			model.addAttribute("categoria", categoria);
			model.addAttribute("idVendedor", idVendedor);
			model.addAttribute("listaCategorias", this.repoCategoria.findAll(PageRequest.of(0, 2)));
			model.addAttribute("usuario", user);
			model.addAttribute("producto", new Producto());
			model.addAttribute("error", "Intente un nombre no registrado");
			return "registroCategoria";
		}
		repoCategoria.save(categoria);
		model.addAttribute("categoria", new Categoria());
		model.addAttribute("listaCategorias", repoCategoria.findAll());
		model.addAttribute("idVendedor", idVendedor);
		model.addAttribute("producto", new Producto());

		return "redirect:/registroCategoria/" + idVendedor;

	}

	private boolean existeCategoria(String nombre) {
		if (repoCategoria.findByNombre(nombre) != null) {
			return true;
		}
		return false;
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/editarCategoria/{id}")
	public String editarCategoria(@PathVariable int id, Model model) {
		
		Categoria c = repoCategoria.findById(id);
		model.addAttribute("categoria", c);
		model.addAttribute("usuario", c.getUsuario());
		model.addAttribute("producto", new Producto());
		return "editarCategoria";
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/modificarCategoria/{id}")
	public String editarCategoria(@Valid Categoria categoria, BindingResult result, @PathVariable int id, Model model) {
		categoria.setUsuario(repoCategoria.findById(id).getUsuario());
		categoria.setId(id);
		if (result.hasErrors()) {
			model.addAttribute("categoria", categoria);
			model.addAttribute("usuario", categoria.getUsuario());
			model.addAttribute("producto", new Producto());
			model.addAttribute("error", " ");
			return "editarCategoria";
		}
		if (existenCategorias(categoria)) {
			model.addAttribute("categoria", categoria);
			model.addAttribute("usuario", categoria.getUsuario());
			model.addAttribute("producto", new Producto());
			model.addAttribute("error", "Ya existe una categoria con el mismo nombre");
			return "editarCategoria";
		}

		repoCategoria.save(categoria);
		model.addAttribute("categoria", new Categoria());
		model.addAttribute("listaCategorias", repoCategoria.findAll());
		model.addAttribute("idVendedor", categoria.getUsuario().getId());
		model.addAttribute("producto", new Producto());
		return "redirect:/registroCategoria/" + categoria.getUsuario().getId();
	}

	private boolean existenCategorias(Categoria categoria) {
		try {
			if (repoCategoria.findByNombre(categoria.getNombre()).getId() != categoria.getId()) {
				return true;
			}

		} catch (Exception e) {
		}
		return false;
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/eliminarCategoria/{id}")
	public String eliminarCategoria(@PathVariable int id, Model model) {
		int idVendedor = repoCategoria.findById(id).getUsuario().getId();
		repoCategoria.deleteById(id);
		model.addAttribute("categoria", new Categoria());
		model.addAttribute("listaCategorias", repoCategoria.findAll());
		model.addAttribute("idVendedor", idVendedor);
		model.addAttribute("producto", new Producto());
		return "redirect:/registroCategoria/" + idVendedor;
	}

}
