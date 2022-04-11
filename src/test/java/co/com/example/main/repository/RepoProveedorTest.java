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

import co.com.example.main.domain.Proveedor;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RepoProveedorTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	RepoProveedor repoProveedor;
	
	private Proveedor getProveedor(String nombre) {
		Proveedor proveedor = new Proveedor();
		proveedor.setNombre(nombre);
		proveedor.setDescripcion("Nombre proveedor");
		return proveedor;
	}
	
	
	
	@Test
	public void testListarVacio() {
		Iterable<Proveedor> lista = repoProveedor.findAll();
		assertThat(lista).isEmpty();
	}
	
	@Test
	public void testGuardar() {
		Proveedor proveedor = entityManager.persist(getProveedor("carlos"));
		assertNotNull(proveedor);
	}
	
	@Test
	public void testBuscarId() {
		Proveedor proveedor = entityManager.persist(getProveedor("carlos"));
		Proveedor b = repoProveedor.findById(proveedor.getId());
		assertThat(proveedor).isEqualTo(b);
	}
	
	@Test
	public void TestModificar() {
		Proveedor proveedor = entityManager.persist(getProveedor("carlos"));
		proveedor.setNombre("t1");
		proveedor.setDescripcion("fffffff");
		Proveedor update = repoProveedor.save(proveedor);
		assertThat(update).isEqualTo(proveedor);
	}
	
	@Test
	public void testEliminar() {
		Proveedor proveedor = entityManager.persist(getProveedor("carlos"));
		repoProveedor.delete(proveedor);
		Proveedor b = repoProveedor.findById(proveedor.getId());
		assertNull(b);
	}
	
	@Test
	public void testListarTodos() {
		entityManager.persist(getProveedor("carlos"));
		entityManager.persist(getProveedor("carlos2"));
		entityManager.persist(getProveedor("carlos3"));
		
		Iterable<Proveedor> lista = repoProveedor.findAll();
		assertThat(lista).isNotEmpty();
	}
	
	@Test
	public void testBuscarNombre() {
		Proveedor proveedor = entityManager.persist(getProveedor("carlos"));
		Proveedor b = repoProveedor.findByNombre(proveedor.getNombre());
		assertThat(proveedor).isEqualTo(b);
	}

}
