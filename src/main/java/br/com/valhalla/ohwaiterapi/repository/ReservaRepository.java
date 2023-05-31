package br.com.valhalla.ohwaiterapi.repository;

import br.com.valhalla.ohwaiterapi.entity.Reserva;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    Reserva findByCodigo(String codigo);

    List<Reserva> findByDataReservaContainingAndStatusIn(String dataReserva, List<String> statusList, Sort sort);
}