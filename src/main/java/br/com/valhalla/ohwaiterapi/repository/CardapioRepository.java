package br.com.valhalla.ohwaiterapi.repository;

import br.com.valhalla.ohwaiterapi.entity.Cardapio;
import br.com.valhalla.ohwaiterapi.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardapioRepository extends JpaRepository<Cardapio, Long> {

    List<Cardapio> findByCategoria(Categoria categoria);

}
