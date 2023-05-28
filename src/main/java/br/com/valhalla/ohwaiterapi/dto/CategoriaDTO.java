package br.com.valhalla.ohwaiterapi.dto;

import br.com.valhalla.ohwaiterapi.entity.Cardapio;
import br.com.valhalla.ohwaiterapi.entity.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {

    private Long id;
    private String nome;

    public static CategoriaDTO toDto(Categoria categoria) {
        return CategoriaDTO.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .build();
    }

    public static Categoria toEntity(CategoriaDTO dto) {
        return Categoria.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .build();
    }

    public static List<CategoriaDTO> toListDto(List<Categoria> categorias){
        List<CategoriaDTO> dtos = new ArrayList<>();
        categorias.forEach(categoria -> {
            dtos.add(toDto(categoria));
        });
        return dtos;
    }
}