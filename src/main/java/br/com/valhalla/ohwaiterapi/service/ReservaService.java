package br.com.valhalla.ohwaiterapi.service;

import br.com.valhalla.ohwaiterapi.dto.ReservaDTO;
import br.com.valhalla.ohwaiterapi.entity.Reserva;
import br.com.valhalla.ohwaiterapi.entity.ReservaCardapio;
import br.com.valhalla.ohwaiterapi.repository.ReservaCardapioRepository;
import br.com.valhalla.ohwaiterapi.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    public ReservaDTO obterReservaPorCodigo(String codigo) {
        Reserva reserva = reservaRepository.findByCodigo(codigo);
        List<ReservaCardapio> cardapios = reservaCardapioRepository.findByReserva(reserva);
        return ReservaDTO.toDTO(reserva, cardapios);
    }

    public List<ReservaDTO> obterTodos(){
        List<ReservaDTO> dtos = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<Reserva> listaReserva = reservaRepository.findAll(sort);
        listaReserva.forEach(reserva -> {
            List<ReservaCardapio> cardapios = reservaCardapioRepository.findByReserva(reserva);
            dtos.add(ReservaDTO.toDTO(reserva, cardapios));
        });
        return dtos;
    }

    public List<ReservaDTO> buscarReservasDeHoje() {
        Date dataHoje = Calendar.getInstance().getTime();

        // Converter a data para o formato adequado
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String dataHojeString = formatter.format(dataHoje);

        List<Reserva> listaReserva = reservaRepository.findByDataReservaContaining(dataHojeString);
        List<ReservaDTO> dtos = new ArrayList<>();

        listaReserva.forEach(reserva -> {
            List<ReservaCardapio> cardapios = reservaCardapioRepository.findByReserva(reserva);
            dtos.add(ReservaDTO.toDTO(reserva, cardapios));
        });

        return dtos;
    }

    public void atualizarStatusReserva(Long id, String novoStatus) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não encontrada"));

        reserva.setStatus(novoStatus);
        reservaRepository.save(reserva);
    }
}
