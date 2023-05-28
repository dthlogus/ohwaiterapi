package br.com.valhalla.ohwaiterapi.controller;

import br.com.valhalla.ohwaiterapi.dto.CategoriaDTO;
import br.com.valhalla.ohwaiterapi.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> salvarOuAlterarCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO categoriaSalva = categoriaService.salvarOuAlterar(categoriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> buscarTodasCategorias() {
        List<CategoriaDTO> categorias = categoriaService.buscarTodos();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obterCategoria(@PathVariable Long id) {
        CategoriaDTO categoriaDTO = categoriaService.obterCategoria(id);
        if (categoriaDTO != null) {
            return ResponseEntity.ok(categoriaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCategoria(@PathVariable Long id) {
        categoriaService.excluirCategoria(id);
        return ResponseEntity.noContent().build();
    }
}