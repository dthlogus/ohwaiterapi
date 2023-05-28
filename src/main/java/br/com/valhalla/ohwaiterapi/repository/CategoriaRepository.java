package br.com.valhalla.ohwaiterapi.repository;

import br.com.valhalla.ohwaiterapi.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByNome(String nome);
}
