package co.com.example.main.controller;

import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.utils.ObjectUtils;

import co.com.example.main.CloudinaryConfig;
import co.com.example.main.domain.Producto;
import co.com.example.main.domain.Usuario;
import co.com.example.main.repository.RepoProducto;
import co.com.example.main.repository.RepoProveedor;
import co.com.example.main.repository.RepoSubcategoria;
import co.com.example.main.repository.RepoUsuario;
import co.com.example.main.security.util.Passgenerator;
import co.com.example.main.security.util.UserAutenticado;

@Controller
public class CtlUsuario {

	@Autowired
	private RepoUsuario repoUsuario;

	@Autowired
	private RepoSubcategoria repoSubcategoria;

	@Autowired
	private RepoProducto repoProducto;

	@Autowired
	private RepoProveedor repoProveedor;

	@Autowired
	private CloudinaryConfig cloudc;
	
	@Autowired
    private Passgenerator passgenerator;
	
	@Autowired
	private UserAutenticado userAutenticado;

	@GetMapping("")
	public String index(Model model) {
		model.addAttribute("usuarioRegistrado", true);
		return "index";
	}
	
	@GetMapping("/home")
	public String inicioSinLogin(Model model, @RequestParam(defaultValue = "0") int page) {
		
		model.addAttribute("usuario",new Usuario());
		model.addAttribute("listaSubcategorias", this.repoSubcategoria.findAll());
		model.addAttribute("listaProveedores", this.repoProveedor.findAll());
		model.addAttribute("listaProductos", this.repoProducto.findAll(PageRequest.of(page, 6)));
		model.addAttribute("producto", new Producto());
		return "home";
	}
	
	@GetMapping("/inicio")
	public String inicio(Model model, @RequestParam(defaultValue = "0") int page) {
		UserDetails user = userAutenticado.getAuth();
		Usuario u = repoUsuario.findByCorreo(user.getUsername());
		model.addAttribute("usuario", u);
		model.addAttribute("listaSubcategorias", this.repoSubcategoria.findAll());
		model.addAttribute("listaProveedores", this.repoProveedor.findAll());
		model.addAttribute("listaProductos", this.repoProducto.findAll(PageRequest.of(page, 6)));
		model.addAttribute("producto", new Producto());
		return "ingresoUsuario";
	}

	
	@GetMapping("/inicio/{idUsuario}")
	public String inicio(Model model, @PathVariable("idUsuario") int idUsuario, @RequestParam(defaultValue = "0") int page) {
		UserDetails user1 = userAutenticado.getAuth();
		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
		if (user.getId()!=idUsuario) {
			return "denegado";
		}
		model.addAttribute("usuario", user);
		model.addAttribute("listaSubcategorias", this.repoSubcategoria.findAll());
		model.addAttribute("listaProveedores", this.repoProveedor.findAll());
		model.addAttribute("listaProductos", this.repoProducto.findAll(PageRequest.of(page, 6)));
		model.addAttribute("producto", new Producto());
		return "ingresoUsuario";
	}

	@GetMapping("/ingreso")
	public String ingresoUsuario(Model model, Usuario usuario, @RequestParam(defaultValue = "0") int page) {
		if (usuario.getCorreo().isEmpty()||usuario.getCorreo().length()<=6 || usuario.getContrasena().length()<7) {
			model.addAttribute("noEncontrado", true);
			model.addAttribute("correoVacio", "Ingrese un correo Válido ");
			if (usuario.getContrasena().length()<7 ) {
				model.addAttribute("noEncontrado", true);
				model.addAttribute("contrasenaVacia", "Ingrese mas de 6 caracteres");
			}
			return "login";
		} 
		if (usuario.getRol().equals("Cliente")) {
			try {
				Usuario u = repoUsuario.findByCorreo(usuario.getCorreo());
				if (usuario.getContrasena().equals(u.getContrasena())) {
					if (u.getRol().equals("Cliente")) {
						model.addAttribute("usuario", u);
						model.addAttribute("listaSubcategorias", this.repoSubcategoria.findAll());
						model.addAttribute("listaProveedores", this.repoProveedor.findAll());
						model.addAttribute("listaProductos", this.repoProducto.findAll(PageRequest.of(page, 6)));
						model.addAttribute("producto", new Producto());
						return "ingresoUsuario";
					} else {
						// no se encontro un cliente por este dni
						model.addAttribute("noEncontrado", true);
						return "login";
					}
				} else {
					model.addAttribute("errorContrasena", true);
					return "login";
				}

			} catch (NullPointerException e) {
				model.addAttribute("noEncontrado", true);
				return "login";
			}
		} else {
			try {
				Usuario u = repoUsuario.findByCorreo(usuario.getCorreo());
				if (u.getContrasena().equals(u.getContrasena())) {
					if (u.getRol().equals("Vendedor")) {
						model.addAttribute("usuario", u);
						model.addAttribute("listaProveedores", this.repoProveedor.findAll());
						model.addAttribute("listaSubcategorias", this.repoSubcategoria.findAll());
						model.addAttribute("listaProductos", this.repoProducto.findAll(PageRequest.of(page, 6)));
						model.addAttribute("producto", new Producto());
						return "ingresoUsuario";
					} else {
						// no se encontro un vendedor por este dni
						model.addAttribute("noEncontrado", true);
						return "login";
					}
				} else {
					model.addAttribute("errorContrasena", true);
					return "login";
				}
			} catch (NullPointerException e) {
				model.addAttribute("noEncontrado", true);
				return "login";
			}
		}
	}

	//public
	@GetMapping("/registroClienteVendedor")
	public String registroClienteVendedor(Model model) {
		return "registroClienteVendedor";
	}

	//public
	@GetMapping("/soporte")
	public String soporte(Model model) {
		return "soporte";
	}

	//public
	@GetMapping("/signup")
	public String login(Model model) {
		model.addAttribute("noEncontrado", false);
		model.addAttribute("errorContrasena", false);
		model.addAttribute("usuario", new Usuario());
		return "login";
	}
	
	//public
		@GetMapping("/login")
		public String l(Model model) {
			model.addAttribute("noEncontrado", false);
			model.addAttribute("errorContrasena", false);
			model.addAttribute("usuario", new Usuario());
			return "login";
		}
	
	//public
		@GetMapping("/logout")
		public String logout(Model model) {
			model.addAttribute("noEncontrado", false);
			model.addAttribute("errorContrasena", false);
			model.addAttribute("usuario", new Usuario());
			return "login";
		}


	//public
	@GetMapping("/QuedateEnCasa")
	public String video(Model model) {
		return "video";
	}

	//public
	@GetMapping("/registroCliente")
	public String registroCliente(Model model) {
		Usuario usuario = new Usuario();
		usuario.setRol("Cliente");
		model.addAttribute("usuario", usuario);
		return "registroUsuario";
	}

	//public
	@GetMapping("/registroVendedor")
	public String registroVendedor(Model model) {
		Usuario usuario = new Usuario();
		usuario.setRol("Vendedor");
		model.addAttribute("usuario", usuario);
		return "registroUsuario";
	}

	@PostMapping("/guardarUsuario")
	public String guardarUsaurio(@Valid Usuario usuario, BindingResult result, Model model, @RequestParam("file") MultipartFile file) {
		try {
			Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
			usuario.setUrlFoto(uploadResult.get("url").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (usuario.getCodigoEmpresa() == null) {
			if (result.hasErrors()) {
				usuario.setRol("Cliente");
				model.addAttribute("usuario", usuario);
				return "registroUsuario";
			}
			if (repoUsuario.findByDNI(usuario.getDNI()) != null) {
				model.addAttribute("error", "Ya existe un usuario con el mismo DNI");
				usuario.setRol("Cliente");
				model.addAttribute("usuario", usuario);
				return "registroUsuario";
			}
			usuario.setRol("Cliente");
			usuario.setContrasena(passgenerator.enciptarPassword(usuario.getContrasena()));
			repoUsuario.save(usuario);
			model.addAttribute("usuarioRegistrado", true);
			return "redirect:/";
		} else {
			if (usuario.getCodigoEmpresa().equalsIgnoreCase("A7B8C9")) {
				usuario.setRol("Vendedor");
				usuario.setContrasena(passgenerator.enciptarPassword(usuario.getContrasena()));
				repoUsuario.save(usuario);
				model.addAttribute("usuarioRegistrado", true);
				return "redirect:/";
			} else {
				usuario.setRol("Vendedor");
				model.addAttribute("usuario", usuario);
				model.addAttribute("errorCodigo", true);
				return "registroUsuario";
			}
		}
	}

	public boolean existenUsuarios(String DNI, int id) {
		if (repoUsuario.findByDNI(DNI).getId() != repoUsuario.findById(id).getId()) {
			return true;
		}
		return false;
	}

	public boolean existeUnUsuario(String DNI) {
		if (repoUsuario.findByDNI(DNI) != null) {
			return true;
		}
		return false;
	}
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/editarPerfil/{idUsuario}")
	public String editarUsuario(Model model, @PathVariable int idUsuario) {
		UserDetails user1 = userAutenticado.getAuth();
		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
		if (user.getId()!=idUsuario) {
			return "denegado";
		}
		model.addAttribute("usuario", this.repoUsuario.findById(user.getId()));
		model.addAttribute("producto", new Producto());
		return "editarUsuario";

	}
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@PostMapping("/modificarUsuario/{idUsuario}")
	public String modificarUsuario(@Valid Usuario usuario, BindingResult result, Model model,
			@PathVariable("idUsuario") int idUsuario, @RequestParam("file") MultipartFile file,
			@RequestParam("cambioUrl") boolean cambioUrl, @RequestParam(defaultValue = "0") int page) {
		UserDetails user1 = userAutenticado.getAuth();
		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
		if (user.getId()!=idUsuario) {
			return "denegado";
		}		usuario.setId(idUsuario);
		if (cambioUrl) {
			try {
				Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
				usuario.setUrlFoto(uploadResult.get("url").toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (result.hasErrors()) {
			usuario.setRol("Cliente");
			model.addAttribute("usuario", usuario);
			model.addAttribute("producto", new Producto());
			return "editarUsuario";
		}
		usuario.setContrasena(passgenerator.enciptarPassword(usuario.getContrasena()));
		this.repoUsuario.save(usuario);
		model.addAttribute("usuario", usuario);
		model.addAttribute("listaProveedores", this.repoProveedor.findAll());
		model.addAttribute("listaSubcategorias", this.repoSubcategoria.findAll());
		model.addAttribute("listaProductos", this.repoProducto.findAll(PageRequest.of(page, 6)));
		model.addAttribute("producto", new Producto());
		return "redirect:/inicio/" + idUsuario;
	}
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/eliminarCuenta/{idUsuario}")
	public String eliminarCuenta(Model model, @PathVariable("idUsuario") int id) {
		UserDetails user1 = userAutenticado.getAuth();
		Usuario user = repoUsuario.findByCorreo(user1.getUsername());
		if (user.getId()!=id) {
			return "denegado";
		}
		id=user.getId();
		this.repoUsuario.delete(this.repoUsuario.findById(id));
		return "redirect:/";
	}

}
