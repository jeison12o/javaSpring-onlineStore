package co.com.example.main.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.com.example.main.domain.DetalleFactura;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RepoDetalleFacturaTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	RepoDetalleFactura repoDetalleFactura;
	
	private DetalleFactura getDetalleFactura() {
		DetalleFactura detalleFactura = new DetalleFactura();
		detalleFactura.setCantidad(3);
		detalleFactura.setFecha(new Date());
		detalleFactura.setValor(5454);
		return detalleFactura;
	}
	
	
	
	@Test
	public void testListarVacio() {
		Iterable<DetalleFactura> lista = repoDetalleFactura.findAll();
		assertThat(lista).isEmpty();
	}
	
	@Test
	public void testGuardar() {
		DetalleFactura detalleFactura = entityManager.persist(getDetalleFactura());
		assertNotNull(detalleFactura);
	}
	
	@Test
	public void testBuscarId() {
		DetalleFactura detalleFactura = entityManager.persist(getDetalleFactura());
		DetalleFactura b = repoDetalleFactura.findById(detalleFactura.getId());
		assertThat(detalleFactura).isEqualTo(b);
	}
	
	@Test
	public void TestModificar() {
		DetalleFactura detalleFactura = entityManager.persist(getDetalleFactura());
		detalleFactura.setCantidad(45);
		DetalleFactura update = repoDetalleFactura.save(detalleFactura);
		assertThat(update).isEqualTo(detalleFactura);
	}
	
	@Test
	public void testEliminar() {
		DetalleFactura detalleFactura = entityManager.persist(getDetalleFactura());
		repoDetalleFactura.delete(detalleFactura);
		assertNull(repoDetalleFactura.findById(detalleFactura.getId()));
	}
	
	@Test
	public void testListarTodos() {
		entityManager.persist(getDetalleFactura());
		entityManager.persist(getDetalleFactura());
		Iterable<DetalleFactura> lista = repoDetalleFactura.findAll();
		assertThat(lista).isNotEmpty();
	}
}
