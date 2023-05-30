package br.com.valhalla.ohwaiterapi.dto;

import br.com.valhalla.ohwaiterapi.entity.Reserva;
import br.com.valhalla.ohwaiterapi.entity.ReservaCardapio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {
    private Long id;
    private String codigo;
    private String status;
    private List<ReservaCardapio> reservaCardapios;
    private Integer tempoTotalPreparo;
    private BigDecimal precoTotal;
    private String nomeCliente;
    private String cpf;
    private String dataReserva;

    public static ReservaDTO toDTO(Reserva reserva, List<ReservaCardapio> reservasCardapiosList){
        return ReservaDTO.builder()
                .id(reserva.getId())
                .codigo(reserva.getCodigo())
                .status(reserva.getStatus())
                .reservaCardapios(reservasCardapiosList)
                .tempoTotalPreparo(reserva.getTempoTotalPreparo())
                .precoTotal(reserva.getPrecoTotal())
                .nomeCliente(reserva.getNomeCliente())
                .cpf(reserva.getCpf())
                .dataReserva(reserva.getDataReserva())
                .build();
    }
}
