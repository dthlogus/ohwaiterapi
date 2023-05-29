package br.com.valhalla.ohwaiterapi.dto;

import br.com.valhalla.ohwaiterapi.entity.Cardapio;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("id")
    private long id;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("urlImage")
    private String urlImage;
    @JsonProperty("descricao")
    private String descricao;
    @JsonProperty("categoria")
    private CategoriaDTO categoria;
    @JsonProperty("preco")
    private BigDecimal preco;
    @JsonProperty("quantidade")
    private int quantidade;


    public static CardapioDTO toDto(Cardapio cardapio) {
        CardapioDTO dto = new CardapioDTO();
        dto.setId(cardapio.getId());
        dto.setNome(cardapio.getNome());
        dto.setUrlImage(cardapio.getUrlImage());
        dto.setDescricao(cardapio.getDescricao());
        dto.setPreco(cardapio.getPreco());
        dto.setQuantidade(cardapio.getQuantidade());
        dto.setCategoria(CategoriaDTO.toDto(cardapio.getCategoria()));
        return dto;
    }

    public static Cardapio toEntity(CardapioDTO dto) {
        Cardapio cardapio = new Cardapio();
        cardapio.setId(dto.getId());
        cardapio.setNome(dto.getNome());
        cardapio.setUrlImage(dto.getUrlImage());
        cardapio.setDescricao(dto.getDescricao());
        cardapio.setPreco(dto.getPreco());
        cardapio.setQuantidade(dto.getQuantidade());
        cardapio.setCategoria(CategoriaDTO.toEntity(dto.getCategoria()));
        return cardapio;
    }

    public static List<CardapioDTO> toListDto(List<Cardapio> cardapios){
        List<CardapioDTO> dtos = new ArrayList<>();
        cardapios.forEach(cardapio -> dtos.add(toDto(cardapio)));
        return dtos;
    }
}
