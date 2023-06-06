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

    @GetMapping("/{codigo}/buscarPorCodigo")
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

    @GetMapping("/{cpf}/buscarPorCpf")
    public ResponseEntity<List<ReservaDTO>> obterReservaPorCpf(@PathVariable String cpf) {
        String cpfFormatado = formatarCPF(cpf); // Formata o CPF com pontuações
        List<ReservaDTO> reservas = reservaService.obterPorCpf(cpfFormatado);
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

    private String formatarCPF(String cpf) {
        // Remove caracteres não numéricos do CPF
        String cpfApenasNumeros = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF possui 11 dígitos
        if (cpfApenasNumeros.length() == 11) {
            // Verifica se o CPF é válido
            if (validarCPF(cpfApenasNumeros)) {
                // Formata o CPF com as pontuações adequadas
                return cpfApenasNumeros.substring(0, 3) + "." +
                        cpfApenasNumeros.substring(3, 6) + "." +
                        cpfApenasNumeros.substring(6, 9) + "-" +
                        cpfApenasNumeros.substring(9);
            } else {
                throw new IllegalArgumentException("CPF inválido");
            }
        } else {
            throw new IllegalArgumentException("CPF inválido");
        }
    }

    private boolean validarCPF(String cpf) {

        // Remove caracteres não numéricos do CPF
        String cpfApenasNumeros = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF possui 11 dígitos
        if (cpfApenasNumeros.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (CPF inválido)
        if (cpfApenasNumeros.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpfApenasNumeros.charAt(i)) * (10 - i);
        }
        int resto = soma % 11;
        int digitoVerificador1 = (resto < 2) ? 0 : 11 - resto;

        // Verifica o primeiro dígito verificador
        if (Character.getNumericValue(cpfApenasNumeros.charAt(9)) != digitoVerificador1) {
            return false;
        }

        // Calcula o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpfApenasNumeros.charAt(i)) * (11 - i);
        }
        resto = soma % 11;
        int digitoVerificador2 = (resto < 2) ? 0 : 11 - resto;

        // Verifica o segundo dígito verificador
        if (Character.getNumericValue(cpfApenasNumeros.charAt(10)) != digitoVerificador2) {
            return false;
        }

        return true;
    }
}
