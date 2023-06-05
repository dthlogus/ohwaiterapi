package br.com.valhalla.ohwaiterapi.controller;

import br.com.valhalla.ohwaiterapi.dto.ReservaDTO;
import br.com.valhalla.ohwaiterapi.entity.Reserva;
import br.com.valhalla.ohwaiterapi.entity.ReservaCardapio;
import br.com.valhalla.ohwaiterapi.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/reservas")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8100/"})
public class ReservaController {

    private final ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<Reserva> salvarReserva(@RequestBody ReservaDTO reservaDTO) {
        Reserva reserva = reservaService.criarReserva(reservaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ReservaDTO> obterReservaPorCodigo(@PathVariable String codigo) {
        ReservaDTO reserva = reservaService.obterReservaPorCodigo(codigo);
        if (reserva != null) {
            return ResponseEntity.ok(reserva);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> obterReservas() {
        List<ReservaDTO> reservas = reservaService.buscarReservasDeHoje();
        if (reservas != null) {
            return ResponseEntity.ok(reservas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/atualizar-status")
    public ResponseEntity<String> atualizarStatusReserva(@PathVariable Long id, @RequestBody String novoStatus) {
        try {
            reservaService.atualizarStatusReserva(id, novoStatus);
            return ResponseEntity.ok("Status da reserva atualizado com sucesso");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
}
