package br.com.valhalla.ohwaiterapi.dto;

import br.com.valhalla.ohwaiterapi.entity.Cardapio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardapioDTO {

    private Long id;
    private String nome;
    private String urlImage;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidade;
    private CategoriaDTO categoria;

    public static CardapioDTO toDto(Cardapio cardapio) {
        return CardapioDTO.builder()
                .id(cardapio.getId())
                .nome(cardapio.getNome())
                .urlImage(cardapio.getUrlImage())
                .descricao(cardapio.getDescricao())
                .preco(cardapio.getPreco())
                .quantidade(cardapio.getQuantidade())
                .categoria(CategoriaDTO.toDto(cardapio.getCategoria()))
                .build();
    }

    public static Cardapio toEntity(CardapioDTO dto) {
        return Cardapio.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .urlImage(dto.getUrlImage())
                .descricao(dto.getDescricao())
                .preco(dto.getPreco())
                .quantidade(dto.getQuantidade())
                .categoria(CategoriaDTO.toEntity(dto.getCategoria()))
                .build();
    }

    public static List<CardapioDTO> toListDto(List<Cardapio> cardapios){
        List<CardapioDTO> dtos = new ArrayList<>();
        cardapios.forEach(cardapio -> {
            dtos.add(toDto(cardapio));
        });
        return dtos;
    }
}
