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

import co.com.example.main.domain.Bodega;
import co.com.example.main.domain.Usuario;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RepoBodegaTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	RepoBodega repoBodega;
	
	@Autowired
	RepoUsuario repoUsuario;
	
	private Bodega getBodega(String nombre) {
		Bodega bodega = new Bodega();
		bodega.setNombre(nombre);
		bodega.setDireccion("dffd");
		bodega.setCapacidad(2);
		bodega.setEspacioDisponible(2);
		return bodega;
	}
	
	
	
	@Test
	public void testListarVacio() {
		Iterable<Bodega> lista = repoBodega.findAll();
		assertThat(lista).isEmpty();
	}
	
	@Test
	public void testGuardar() {
		Bodega bodega = entityManager.persist(getBodega("primera"));
		assertNotNull(bodega);
	}
	
	@Test
	public void testBuscarId() {
		Bodega bodega = entityManager.persist(getBodega("primera"));
		Bodega b = repoBodega.findById(bodega.getId());
		assertThat(bodega).isEqualTo(b);
	}
	
	@Test
	public void TestModificar() {
		Bodega bodega = entityManager.persist(getBodega("primera"));
		bodega.setNombre("t1");
		bodega.setCapacidad(3);
		Bodega update = repoBodega.save(bodega);
		assertThat(update).isEqualTo(bodega);
	}
	
	@Test
	public void testEliminar() {
		Bodega bodega = entityManager.persist(getBodega("primera"));
		repoBodega.delete(bodega);
		Bodega b = repoBodega.findById(bodega.getId());
		assertNull(b);
	}
	
	@Test
	public void testListarTodos() {
		entityManager.persist(getBodega("primera"));
		entityManager.persist(getBodega("segunda"));
		entityManager.persist(getBodega("tercera"));
		
		Iterable<Bodega> lista = repoBodega.findAll();
		assertThat(lista).isNotEmpty();
	}
	
	@Test
	public void testListarTodosUsuario() {
		Usuario user = new Usuario();
		user.setDNI("1");
		user.setNombre("junior Alexis");
		user.setApellido("llanten Velez");
		user.setTelefono("575456732");
		user.setCorreo("j@gmail.com");
		user.setRol("Cliente");
		user.setUrlFoto("www");
		user.setDireccion("Direccion test 123");
		repoUsuario.save(user);
		
		Bodega b1= getBodega("primera");
		b1.setUsuario(user);
		Bodega b2= getBodega("segunda");
		b2.setUsuario(user);
		Bodega b3= getBodega("tercera");
		b3.setUsuario(user);
		
		entityManager.persist(b1);
		entityManager.persist(getBodega("segunda"));
		entityManager.persist(getBodega("tercera"));
		
		Iterable<Bodega> lista = repoBodega.findByUsuario(user);
		assertThat(lista).isNotEmpty();
	}
	
}
