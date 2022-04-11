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
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity

public class Producto {

	private static final long serialVersionUID = 2679541203984681110L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min = 3, max = 50, message = "Ingrese entre 3 y 50 caracteres. ")
	private String nombre;
	
	@Size(min = 5, max = 50, message = "Ingrese entre 5 y 50 caracteres. ")
	private String descripcion;

	@Min(value = 0, message = "La cantidad debe ser de al menos 0. ")
	private int cantidad;

	@Min(value = 1, message = "El precio debe ser de al menos 1. ")
	private double precio;
	
	private double precioConIVA;
	
	private boolean IVA = false;

	@Column(length = 255)
	@NotBlank(message = "Debe subir una foto desde su PC. ")
	private String urlFoto;

	@OneToMany(mappedBy = "producto", cascade = CascadeType.REMOVE)
	private List<Carrito> carrito;

	@OneToMany(mappedBy = "producto", cascade = CascadeType.REMOVE)
	private List<DetalleFactura> detalleFactura;

	@ManyToOne
	private Proveedor proveedor;

	@ManyToOne
	private Subcategoria subcategoria;

	@ManyToOne
	private Bodega bodega;

	@ManyToOne
	private Usuario vendedor;

	@Transient
	private int idSubcategoria;

	@Transient
	private int idProveedor;

	@Transient
	private int idBodega;

	@Transient
	private double precioMinimo;

	@Transient
	private double precioMaximo;

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getPrecioConIVA() {
		return precioConIVA;
	}

	public void setPrecioConIVA(double precioConIVA) {
		this.precioConIVA = precioConIVA;
	}

	public boolean isIVA() {
		return IVA;
	}

	public void setIVA(boolean iVA) {
		IVA = iVA;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public List<Carrito> getCarrito() {
		return carrito;
	}

	public void setCarrito(List<Carrito> carrito) {
		this.carrito = carrito;
	}

	public List<DetalleFactura> getDetalleFactura() {
		return detalleFactura;
	}

	public void setDetalleFactura(List<DetalleFactura> detalleFactura) {
		this.detalleFactura = detalleFactura;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Subcategoria getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(Subcategoria subcategoria) {
		this.subcategoria = subcategoria;
	}

	public Bodega getBodega() {
		return bodega;
	}

	public void setBodega(Bodega bodega) {
		this.bodega = bodega;
	}

	public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}

	public int getIdSubcategoria() {
		return idSubcategoria;
	}

	public void setIdSubcategoria(int idSubcategoria) {
		this.idSubcategoria = idSubcategoria;
	}

	public int getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

	public int getIdBodega() {
		return idBodega;
	}

	public void setIdBodega(int idBodega) {
		this.idBodega = idBodega;
	}

	public double getPrecioMinimo() {
		return precioMinimo;
	}

	public void setPrecioMinimo(double precioMinimo) {
		this.precioMinimo = precioMinimo;
	}

	public double getPrecioMaximo() {
		return precioMaximo;
	}

	public void setPrecioMaximo(double precioMaximo) {
		this.precioMaximo = precioMaximo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}