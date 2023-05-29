package br.com.valhalla.ohwaiterapi.controller;

import br.com.valhalla.ohwaiterapi.dto.CategoriaDTO;
import br.com.valhalla.ohwaiterapi.exception.JaExisteException;
import br.com.valhalla.ohwaiterapi.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<?> salvarCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        try {
            CategoriaDTO categoriaSalva = categoriaService.salvarOuAlterar(categoriaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
        } catch (JaExisteException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()); // ou uma mensagem de erro personalizada
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro desconhecido"); // ou uma mensagem de erro personalizada
        }
    }

    @PutMapping
    public ResponseEntity<?> alterarCategoria(@RequestBody CategoriaDTO categoriaDTO){
        try {
            CategoriaDTO categoriaEditada = categoriaService.salvarOuAlterar(categoriaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoriaEditada);
        } catch (JaExisteException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()); // ou uma mensagem de erro personalizada
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro desconhecido"); // ou uma mensagem de erro personalizada
        }
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