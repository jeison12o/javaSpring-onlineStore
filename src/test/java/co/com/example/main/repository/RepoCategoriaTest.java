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

import co.com.example.main.domain.Categoria;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RepoCategoriaTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	RepoCategoria repoCategoria;
	
	/*
	@Autowired
	RepoUsuario repoUsuario;
	*/
	
	private Categoria getCategoria(String nombre, String dni) {
		/*
		Usuario user = new Usuario();
		user.setDNI(dni);
		user.setNombre("j");
		user.setApellido("j");
		user.setTelefono("5754567");
		user.setCorreo("j@gmail.com");
		user.setRol("Cliente");
		user.setUrlFoto("www");
		user.setDireccion("dffd");
		user = repoUsuario.save(user);
		*/
		Categoria categoria = new Categoria();
		categoria.setNombre(nombre);
		//categoria.setUsuario(user);
		return categoria;
	}
	
	
	
	@Test
	public void testListarVacio() {
		Iterable<Categoria> lista = repoCategoria.findAll();
		assertThat(lista).isEmpty();
	}
	
	@Test
	public void testGuardar() {
		Categoria cate = entityManager.persist(getCategoria("primera", "1"));
		assertNotNull(cate);
	}
	
	@Test
	public void testBuscarId() {
		Categoria cate = entityManager.persist(getCategoria("primera", "1"));
		Categoria  b = repoCategoria.findById(cate.getId());
		assertThat(cate).isEqualTo(b);
	}
	
	@Test
	public void TestModificar() {
		Categoria cate = entityManager.persist(getCategoria("primera", "1"));
		cate.setNombre("t");
		Categoria update = repoCategoria.save(cate);
		assertThat(update).isEqualTo(cate);
	}
	
	@Test
	public void testEliminar() {
		Categoria cate = entityManager.persist(getCategoria("primera", "1"));
		repoCategoria.delete(cate);
		Categoria b = repoCategoria.findById(cate.getId());
		assertNull(b);
	}
	
	@Test
	public void testListarTodos() {
		entityManager.persist(getCategoria("primera", "1"));
		Iterable<Categoria> lista = repoCategoria.findAll();
		assertThat(lista).isNotEmpty();
	}
	
	@Test
	public void testBuscarNombre() {
		Categoria cate = entityManager.persist(getCategoria("tercera", "3"));
		Categoria  b = repoCategoria.findByNombre(cate.getNombre());
		assertThat(cate).isEqualTo(b);
	}

}
