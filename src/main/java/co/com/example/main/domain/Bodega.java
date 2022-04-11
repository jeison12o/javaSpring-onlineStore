package co.com.example.main.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
public class Bodega {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true)
	@Size(min = 3, max = 30, message = "Ingrese entre 3 y 30 caracteres. ")
	private String nombre;

	@Size(min = 3, max = 35, message = "Ingrese entre 3 y 35 caracteres. ")
	private String direccion;
	@Min(1)
	private int capacidad;

	private int espacioDisponible;

	@OneToMany(mappedBy = "bodega", cascade = CascadeType.REMOVE)
	private List<Producto> producto;

	@ManyToOne
	private Usuario usuario;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public int getEspacioDisponible() {
		return espacioDisponible;
	}

	public void setEspacioDisponible(int espacioDisponible) {
		this.espacioDisponible = espacioDisponible;
	}

	public List<Producto> getProducto() {
		return producto;
	}

	public void setProducto(List<Producto> producto) {
		this.producto = producto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

}
