package br.com.valhalla.ohwaiterapi.repository;

import br.com.valhalla.ohwaiterapi.entity.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardapioRepository extends JpaRepository<Cardapio, Long> {
}
