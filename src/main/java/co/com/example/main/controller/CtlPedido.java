package co.com.example.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import co.com.example.main.domain.Producto;
import co.com.example.main.domain.Usuario;
import co.com.example.main.repository.RepoPedido;
import co.com.example.main.repository.RepoUsuario;
import co.com.example.main.security.util.UserAutenticado;

@Controller
public class CtlPedido {

	@Autowired
	private RepoPedido repoPedido;

	@Autowired
	private UserAutenticado userAutenticado;
	
	@Autowired
	private RepoUsuario repoUsuario;
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/visualizarPedidos/{idUsuario}")
	public String visualizarPedidos(Model model, @PathVariable("idUsuario") int idUsuario) {
		UserDetails user1 = userAutenticado.getAuth();
		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
		if (user.getId()!=idUsuario) {
			return "denegado";
		}
		model.addAttribute("usuario", user);
		model.addAttribute("listaPedidos", this.repoPedido.findByUsuario(PageRequest.of(0, 6), user));
		model.addAttribute("producto", new Producto());
		return "visualizarPedidos";
	}
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/visualizarPedidos/{idUsuario}/page/{page}")
	public String visualizarPagPedidos(Model model, @PathVariable("idUsuario") int idUsuario,
			@PathVariable("page") int page) {
		UserDetails user1 = userAutenticado.getAuth();
		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
		if (user.getId()!=idUsuario) {
			return "denegado";
		}		
		model.addAttribute("usuario", user);
		model.addAttribute("listaPedidos", this.repoPedido.findByUsuario(PageRequest.of(page, 6), user));
		model.addAttribute("producto", new Producto());
		return "visualizarPedidos";
	}

}
