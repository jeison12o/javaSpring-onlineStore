package co.com.example.main.repository;

import org.springframework.data.repository.CrudRepository;

import co.com.example.main.domain.Usuario;

public interface RepoUsuario extends CrudRepository<Usuario, Integer> {

	Usuario findByDNI(String dni);
	
	Usuario findByCorreo(String correo);

	Usuario findById(int id);

}
