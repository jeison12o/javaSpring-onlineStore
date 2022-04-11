package co.com.example.main.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import co.com.example.main.domain.DetalleFactura;
import co.com.example.main.domain.Facturaa;

public interface RepoDetalleFactura extends JpaRepository<DetalleFactura, Integer>{

	Page<DetalleFactura> findByFactura(Pageable p, Facturaa f);
	
	List<DetalleFactura> findByFactura(Facturaa f);
	
	DetalleFactura findById(int id);
}
