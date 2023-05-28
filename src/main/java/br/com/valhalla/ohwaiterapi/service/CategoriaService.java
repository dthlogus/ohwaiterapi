package br.com.valhalla.ohwaiterapi.service;

import br.com.valhalla.ohwaiterapi.dto.CategoriaDTO;
import br.com.valhalla.ohwaiterapi.entity.Categoria;
import br.com.valhalla.ohwaiterapi.exception.JaExisteException;
import br.com.valhalla.ohwaiterapi.repository.CategoriaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaDTO salvarOuAlterar(CategoriaDTO categoriaDTO) {
        Categoria categoria = CategoriaDTO.toEntity(categoriaDTO);
        List<Categoria> categoriasExistentes = categoriaRepository.findByNome(categoriaDTO.getNome());
        if (!categoriasExistentes.isEmpty()){
            throw new JaExisteException("Categoria já existe");
        }
        Categoria categoriaSalva = categoriaRepository.save(categoria);
        return CategoriaDTO.toDto(categoriaSalva);
    }

    public List<CategoriaDTO> buscarTodos() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<Categoria> categorias = categoriaRepository.findAll(sort);
        return categorias.stream()
                .map(CategoriaDTO::toDto)
                .collect(Collectors.toList());
    }

    public CategoriaDTO obterCategoria(Long id) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
        return categoriaOptional.map(CategoriaDTO::toDto).orElse(null);
    }

    public void excluirCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
}