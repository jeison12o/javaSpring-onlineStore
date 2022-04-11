package co.com.example.main.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.example.main.domain.Producto;
import co.com.example.main.domain.Proveedor;
import co.com.example.main.domain.Subcategoria;

public interface RepoProducto extends JpaRepository<Producto, Integer> {

	Producto findById(int id);

	@Query("Select p from Producto p where p.nombre like '% ?1 %'")
	public Iterable<Producto> buscarProductoConPalabra(String titulo);

	@Query("Select p from Producto p order by p.precio ASC")
	public Iterable<Producto> ordenarPorPrecioAsc();

	@Query("Select p from Producto p order by p.precio DESC")
	public Iterable<Producto> ordenarPorPrecioDesc();

	@Query("Select p from Producto p join Subcategoria s on s.id=p.subcategoria where s.nombre=' ?1 ' ")
	public Iterable<Producto> ordenarPorSubcategoria(String nombreCategoria);

	@Query("Select p from Producto p join Proveedor pr on pr.id=p.proveedor where pr.nombre=' ?1 ' ")
	public Iterable<Producto> ordenarPorProveedor(String nombreProveedor);

//	Page<Producto> findByVendedor(Pageable page, Usuario usuario);

	Page<Producto> findAll(Pageable page);

	public Iterable<Producto> findAllByNombreContainingIgnoreCase(String nombre);

	public Iterable<Producto> findAllByDescripcionContainingIgnoreCase(String descripcion);

	public Iterable<Producto> findAllBySubcategoria(Subcategoria s);

	public Iterable<Producto> findAllByProveedor(Proveedor p);

	public Iterable<Producto> findAllBySubcategoriaOrProveedor(Subcategoria s, Proveedor p);

	public Producto findByNombre(String nombre);
}
