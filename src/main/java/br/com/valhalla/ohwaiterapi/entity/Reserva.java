package br.com.valhalla.ohwaiterapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private int tempoTotalPreparo;

    @Column(nullable = false)
    private BigDecimal precoTotal;

    @Column(nullable = false)
    private String nomeCliente;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String dataReserva;

    @OneToMany(mappedBy = "reserva")
    @JsonManagedReference
    private List<ReservaCardapio> reservaCardapios;
}
