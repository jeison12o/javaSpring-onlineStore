package co.com.example.main.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true)
	private String DNI;

	@Size(min = 3, max = 40, message = "Ingrese entre 3 y 40 letras")
	private String nombre;
	@Size(min = 3, max = 40, message = "Ingrese entre 3 y 40 letras")
	private String apellido;

	@Size(min = 7, max = 12, message = "Ingrese entre 7 y 12 letras")
	private String telefono;

	@Size(min = 10, max = 40, message = "Ingrese entre 10 y 40 letras")
	private String direccion;

	@Email(message = "Ingrese un correo Válido")
	private String correo;
	
	private String contrasena;

	private String rol;

	//@NotBlank(message = "Debe Elegir una foto")
	private String urlFoto;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
	private List<Pedido> pedido;

	@OneToMany(mappedBy = "comprador", cascade = CascadeType.REMOVE)
	private List<Facturaa> facturaComprador;

	@OneToMany(mappedBy = "vendedor", cascade = CascadeType.REMOVE)
	private List<DetalleFactura> detalleFacturaVendedor;

	// codigoEmpresa es un campo disponible en el registro usuario
	// solo si quien se quiere registrar es un vendedor, el codigoEmpresa
	// sería un código cualquiera inventado por nosotros, a la hora de crear
	// el usuario, validaríamos que el código ingresado sea correcto.
	@Transient
	private String codigoEmpresa;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
	private List<Bodega> bodega;

	@OneToMany(mappedBy = "vendedor", cascade = CascadeType.REMOVE)
	private List<Producto> producto;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
	private List<Categoria> categoria;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
	private List<Proveedor> proveedor;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
	private List<Subcategoria> subcategoria;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
	private List<Carrito> carrito;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public List<Pedido> getPedido() {
		return pedido;
	}

	public void setPedido(List<Pedido> pedido) {
		this.pedido = pedido;
	}

	public List<Facturaa> getFacturaComprador() {
		return facturaComprador;
	}

	public void setFacturaComprador(List<Facturaa> facturaComprador) {
		this.facturaComprador = facturaComprador;
	}

	public List<DetalleFactura> getDetalleFacturaVendedor() {
		return detalleFacturaVendedor;
	}

	public void setDetalleFacturaVendedor(List<DetalleFactura> detalleFacturaVendedor) {
		this.detalleFacturaVendedor = detalleFacturaVendedor;
	}

	public String getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public void setCodigoEmpresa(String codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}

	public List<Bodega> getBodega() {
		return bodega;
	}

	public void setBodega(List<Bodega> bodega) {
		this.bodega = bodega;
	}

	public List<Producto> getProducto() {
		return producto;
	}

	public void setProducto(List<Producto> producto) {
		this.producto = producto;
	}

	public List<Categoria> getCategoria() {
		return categoria;
	}

	public void setCategoria(List<Categoria> categoria) {
		this.categoria = categoria;
	}

	public List<Proveedor> getProveedor() {
		return proveedor;
	}

	public void setProveedor(List<Proveedor> proveedor) {
		this.proveedor = proveedor;
	}

	public List<Subcategoria> getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(List<Subcategoria> subcategoria) {
		this.subcategoria = subcategoria;
	}

	public List<Carrito> getCarrito() {
		return carrito;
	}

	public void setCarrito(List<Carrito> carrito) {
		this.carrito = carrito;
	}

	
	
}
