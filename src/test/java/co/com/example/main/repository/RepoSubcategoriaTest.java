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

import co.com.example.main.domain.Subcategoria;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RepoSubcategoriaTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	RepoSubcategoria repoSubcategoria;
	
	private Subcategoria getSubcategoria(String nombre) {
		/*
		Usuario user = new Usuario();
		user.setDNI("1");
		user.setNombre("j");
		user.setApellido("j");
		user.setTelefono("5754567");
		user.setCorreo("j@gmail.com");
		user.setRol("Cliente");
		user.setUrlFoto("www");
		user.setDireccion("dffd");
		
		
		Categoria categoria = new Categoria();
		categoria.setNombre("primera");
		categoria.setUsuario(user);
		*/
		
		Subcategoria subcategoria = new Subcategoria();
		subcategoria.setNombre(nombre);
		//subcategoria.setCategoria(categoria);
		//subcategoria.setUsuario(user);
		return subcategoria;
	}
	
	
	
	@Test
	public void testListarVacio() {
		Iterable<Subcategoria> lista = repoSubcategoria.findAll();
		assertThat(lista).isEmpty();
	}
	
	@Test
	public void testGuardar() {
		Subcategoria sub = entityManager.persist(getSubcategoria("primera"));
		assertNotNull(sub);
	}
	
	@Test
	public void testBuscarId() {
		Subcategoria sub = entityManager.persist(getSubcategoria("primera"));
		Subcategoria  b = repoSubcategoria.findById(sub.getId());
		assertThat(sub).isEqualTo(b);
	}
	
	@Test
	public void TestModificar() {
		Subcategoria sub = entityManager.persist(getSubcategoria("primera"));
		sub.setNombre("t");
		Subcategoria update = repoSubcategoria.save(sub);
		assertThat(update).isEqualTo(sub);
	}
	
	@Test
	public void testEliminar() {
		Subcategoria sub = entityManager.persist(getSubcategoria("primera"));
		repoSubcategoria.delete(sub);
		Subcategoria b = repoSubcategoria.findById(sub.getId());
		assertNull(b);
	}
	
	@Test
	public void testListarTodos() {
		Subcategoria sub = entityManager.persist(getSubcategoria("primera"));
		Iterable<Subcategoria> lista = repoSubcategoria.findAll();
		assertThat(lista).isNotEmpty();
	}
	
	
}
