package br.com.valhalla.ohwaiterapi.repository;

import br.com.valhalla.ohwaiterapi.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
