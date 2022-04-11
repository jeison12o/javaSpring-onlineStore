package co.com.example.main.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import co.com.example.main.domain.Pedido;
import co.com.example.main.domain.Usuario;

public interface RepoPedido extends JpaRepository<Pedido, Integer>{

	Page<Pedido> findByUsuario(Pageable page, Usuario user);
	Pedido findById(int id);
}
