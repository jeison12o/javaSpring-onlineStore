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

import co.com.example.main.domain.Carrito;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RepoCarritoTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	RepoCarrito repoCarrito;
	
	private Carrito getCarrito() {
		Carrito carrito = new Carrito();
		return carrito;
	}
	
	
	
	@Test
	public void testListarVacio() {
		Iterable<Carrito> lista = repoCarrito.findAll();
		assertThat(lista).isEmpty();
	}
	
	@Test
	public void testGuardar() {
		Carrito carrito = entityManager.persist(getCarrito());
		assertNotNull(carrito);
	}
	
	@Test
	public void testBuscarId() {
		Carrito carrito = entityManager.persist(getCarrito());
		Carrito b = repoCarrito.findById(carrito.getId());
		assertThat(carrito).isEqualTo(b);
	}
	
	@Test
	public void TestModificar() {
		Carrito carrito = entityManager.persist(getCarrito());
		carrito.setCantidad(34);
		Carrito update = repoCarrito.save(carrito);
		assertThat(update).isEqualTo(carrito);
	}
	
	@Test
	public void testEliminar() {
		Carrito carrito = entityManager.persist(getCarrito());
		repoCarrito.delete(carrito);
		assertNull(repoCarrito.findById(carrito.getId()));
	}
	
	@Test
	public void testListarTodos() {
		entityManager.persist(getCarrito());
		
		Iterable<Carrito> lista = repoCarrito.findAll();
		assertThat(lista).isNotEmpty();
	}
	
}
