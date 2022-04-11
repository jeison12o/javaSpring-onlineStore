package co.com.example.main.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import co.com.example.main.domain.Subcategoria;

public interface RepoSubcategoria extends JpaRepository<Subcategoria, Integer> {

	Subcategoria findById(int id);

	Page<Subcategoria> findAll(Pageable page);

	Subcategoria findByNombre(String nombre);

}
