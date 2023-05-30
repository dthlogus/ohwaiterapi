package br.com.valhalla.ohwaiterapi.service;

import br.com.valhalla.ohwaiterapi.dto.ReservaDTO;
import br.com.valhalla.ohwaiterapi.entity.Reserva;
import br.com.valhalla.ohwaiterapi.entity.ReservaCardapio;
import br.com.valhalla.ohwaiterapi.repository.ReservaCardapioRepository;
import br.com.valhalla.ohwaiterapi.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaCardapioRepository reservaCardapioRepository;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository, ReservaCardapioRepository reservaCardapioRepository) {
        this.reservaRepository = reservaRepository;
        this.reservaCardapioRepository = reservaCardapioRepository;
    }

    public Reserva criarReserva(ReservaDTO reservaDTO) {
        Reserva reserva = new Reserva();
        reserva.setCodigo(reservaDTO.getCodigo());
        reserva.setStatus(reservaDTO.getStatus());
        reserva.setTempoTotalPreparo(reservaDTO.getTempoTotalPreparo());
        reserva.setPrecoTotal(reservaDTO.getPrecoTotal());
        reserva.setNomeCliente(reservaDTO.getNomeCliente());
        reserva.setCpf(reservaDTO.getCpf());
        reserva.setDataReserva(reservaDTO.getDataReserva());

        reserva = reservaRepository.save(reserva); // Salva a reserva no banco de dados

        List<ReservaCardapio> reservaCardapios = reservaDTO.getReservaCardapios();
        if (reservaCardapios != null) {
            for (ReservaCardapio reservaCardapio : reservaCardapios) {
                reservaCardapio.setReserva(reserva); // Associa a reserva aos objetos ReservaCardapio
                reservaCardapioRepository.save(reservaCardapio); // Salva cada objeto ReservaCardapio individualmente
            }
        }

        reserva.setReservaCardapios(reservaCardapios);
        return reserva;
    }

    public Reserva obterReservaPorCodigo(String codigo) {
        return reservaRepository.findByCodigo(codigo);
    }

    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id).orElse(null);
        if (reserva == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não encontrada");
        }
        reserva.setStatus("CANCELADO");
        reservaRepository.save(reserva);
    }
}
