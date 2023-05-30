package br.com.valhalla.ohwaiterapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cardapio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    @Column(length = 500)
    private String urlImage;
    @Column(length = 1000)
    private String descricao;
    private BigDecimal preco;
    private Integer quantidade = 1;
    private Integer tempoDePreparo;
    @OneToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
