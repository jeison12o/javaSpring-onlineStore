package co.com.example.main.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import co.com.example.main.domain.Carrito;
import co.com.example.main.domain.Usuario;

public interface RepoCarrito extends JpaRepository<Carrito, Integer>{
	
	List<Carrito> findByUsuario(Usuario user);
	
	Page<Carrito> findByUsuario(Pageable page, Usuario user);
	Carrito findById(int id);
}
