package co.com.example.main.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int cantidadArticulos;
	
	@ManyToOne
	private Usuario usuario;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.REMOVE)
	private List<Facturaa> factura;
	
	private double valorTotal;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCantidadArticulos() {
		return cantidadArticulos;
	}

	public void setCantidadArticulos(int cantidadArticulos) {
		this.cantidadArticulos = cantidadArticulos;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Facturaa> getFactura() {
		return factura;
	}

	public void setFactura(List<Facturaa> factura) {
		this.factura = factura;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	
	
}
