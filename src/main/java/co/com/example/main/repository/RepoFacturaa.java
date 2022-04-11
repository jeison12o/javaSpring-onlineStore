package co.com.example.main.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import co.com.example.main.domain.Facturaa;
import co.com.example.main.domain.Usuario;

public interface RepoFacturaa extends JpaRepository<Facturaa, Integer>{

	Facturaa findById(int id);
	
	Page<Facturaa> findByComprador(Pageable page, Usuario user);
	
	List<Facturaa> findByComprador(Usuario user);
	
}
