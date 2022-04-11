package co.com.example.main.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.com.example.main.domain.Producto;
import co.com.example.main.domain.Proveedor;
import co.com.example.main.domain.Subcategoria;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RepoProductoTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	RepoProducto repoProducto;
	
	@Autowired
	RepoSubcategoria repoSubcategoria;
	
	@Autowired
	RepoProveedor repoProveedor;
	
	private Producto getProducto(String nombre) {
		Producto producto = new Producto();
		producto.setNombre(nombre);
		producto.setDescripcion("Esto es una descripcion");
		producto.setCantidad(4);
		producto.setPrecio(200000);
		producto.setUrlFoto("www");
		return producto;
	}
	
	
	
	@Test
	public void testListarVacio() {
		Iterable<Producto> lista = repoProducto.findAll();
		assertThat(lista).isEmpty();
	}
	
	@Test
	public void testGuardar() {
		Producto producto = entityManager.persist(getProducto("azucar"));
		assertNotNull(producto);
	}
	
	@Test
	public void testBuscarId() {
		Producto producto = entityManager.persist(getProducto("azucar"));
		Producto b = repoProducto.findById(producto.getId());
		assertThat(producto).isEqualTo(b);
	}
	
	@Test
	public void TestModificar() {
		Producto producto = entityManager.persist(getProducto("azucar"));
		producto.setCantidad(5);
		Producto update = repoProducto.save(producto);
		assertThat(update).isEqualTo(producto);
	}
	
	@Test
	public void testEliminar() {
		Producto producto = entityManager.persist(getProducto("azucar"));
		repoProducto.delete(producto);
		Producto b = repoProducto.findById(producto.getId());
		assertNull(b);
	}
	
	@Test
	public void testListarTodos() {
		entityManager.persist(getProducto("azucar"));
		entityManager.persist(getProducto("panela"));
		
		Iterable<Producto> lista = repoProducto.findAll();
		assertThat(lista).isNotEmpty();
	}
	
	@Test
	public void testListarNombre() {
		entityManager.persist(getProducto("azucar"));
		entityManager.persist(getProducto("panela"));
		
		Iterable<Producto> lista = repoProducto.findAllByNombreContainingIgnoreCase("azu");
		assertThat(lista).isNotEmpty();
	}
	
	@Test
	public void testListarTodosOrdenAsc() {
		entityManager.persist(getProducto("azucar"));
		entityManager.persist(getProducto("panela"));
		
		Iterable<Producto> lista = repoProducto.ordenarPorPrecioAsc();
		assertThat(lista).isNotEmpty();
	}
	
	@Test
	public void testListarTodosOrdenDes() {
		entityManager.persist(getProducto("azucar"));
		entityManager.persist(getProducto("panela"));
		
		Iterable<Producto> lista = repoProducto.ordenarPorPrecioDesc();
		assertThat(lista).isNotEmpty();
	}
	
	@Test
	public void testListarTodosSubcategoria() {
		Subcategoria subcategoria = new Subcategoria();
		subcategoria.setNombre("subcategoria ensayo");
		repoSubcategoria.save(subcategoria);
		
		Producto p1 = getProducto("azucar");
		p1.setSubcategoria(subcategoria);
		Producto p2 = getProducto("panela");
		p2.setSubcategoria(subcategoria);
		entityManager.persist(p1);
		entityManager.persist(p2);
		
		Iterable<Producto> lista = repoProducto.findAllBySubcategoria(subcategoria);
		assertThat(lista).isNotEmpty();
	}
	
	@Test
	public void testListarTodosProovedor() {
		Proveedor proveedor = new Proveedor();
		proveedor.setNombre("carlos");
		proveedor.setDescripcion("fgfg");
		repoProveedor.save(proveedor);
		
		Producto p1 = getProducto("azucar");
		p1.setProveedor(proveedor);
		Producto p2 = getProducto("panela");
		p2.setProveedor(proveedor);
		entityManager.persist(p1);
		entityManager.persist(p2);
		
		Iterable<Producto> lista = repoProducto.findAllByProveedor(proveedor);
		assertThat(lista).isNotEmpty();
	}
}
