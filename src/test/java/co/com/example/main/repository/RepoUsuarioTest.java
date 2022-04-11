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

import co.com.example.main.domain.Usuario;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RepoUsuarioTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	RepoUsuario repoUsuario;
	
	private Usuario getUsuario(String dni) {
		Usuario user = new Usuario();
		user.setDNI(dni);
		user.setNombre("junior alexis"+dni);
		user.setApellido("llanten velez"+dni);
		user.setTelefono("575456732");
		user.setCorreo("j"+dni+"@gmail.com");
		user.setRol("Cliente");
		user.setUrlFoto("www");
		user.setDireccion("Direccion de prueba 7");
		return user;
	}
	
	
	
	@Test
	public void testListarVacio() {
		Iterable<Usuario> lista = repoUsuario.findAll();
		assertThat(lista).isEmpty();
	}
	
	@Test
	public void testGuardar() {
		Usuario usuario = entityManager.persist(getUsuario("1"));
		assertNotNull(usuario);
	}
	
	@Test
	public void testBuscarId() {
		Usuario usuario = entityManager.persist(getUsuario("1"));
		Usuario b = repoUsuario.findById(usuario.getId());
		assertThat(usuario).isEqualTo(b);
	}
	
	@Test
	public void TestModificar() {
		Usuario usuario = entityManager.persist(getUsuario("1"));
		usuario.setNombre("t1");
		usuario.setApellido("t1");
		Usuario update = repoUsuario.save(usuario);
		assertThat(update).isEqualTo(usuario);
	}
	
	@Test
	public void testEliminar() {
		Usuario usuario = entityManager.persist(getUsuario("1"));
		repoUsuario.delete(usuario);
		Usuario b = repoUsuario.findById(usuario.getId());
		assertNull(b);
	}
	
	@Test
	public void testListarTodos() {
		entityManager.persist(getUsuario("1"));
		entityManager.persist(getUsuario("2"));
		entityManager.persist(getUsuario("3"));
		
		Iterable<Usuario> lista = repoUsuario.findAll();
		assertThat(lista).isNotEmpty();
	}
	
	@Test
	public void testBuscarDni() {
		Usuario usuario = entityManager.persist(getUsuario("1"));
		Usuario b = repoUsuario.findByDNI(usuario.getDNI());
		assertThat(usuario).isEqualTo(b);
	}
}
