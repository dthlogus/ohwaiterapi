package br.com.valhalla.ohwaiterapi.repository;

import br.com.valhalla.ohwaiterapi.entity.Reserva;
import br.com.valhalla.ohwaiterapi.entity.ReservaCardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaCardapioRepository extends JpaRepository<ReservaCardapio, Integer> {

    List<ReservaCardapio> findByReserva(Reserva reserva);
}
