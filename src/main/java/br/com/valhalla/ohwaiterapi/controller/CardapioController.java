package br.com.valhalla.ohwaiterapi.controller;

import br.com.valhalla.ohwaiterapi.dto.CardapioDTO;
import br.com.valhalla.ohwaiterapi.entity.Categoria;
import br.com.valhalla.ohwaiterapi.exception.JaExisteException;
import br.com.valhalla.ohwaiterapi.service.CardapioService;
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
@RequestMapping("/cardapios")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8100/"})
public class CardapioController {

    private final CardapioService cardapioService;

    public CardapioController(CardapioService cardapioService) {
        this.cardapioService = cardapioService;
    }

    @PostMapping
    public ResponseEntity<?> salvarCardapio(@Valid @RequestBody CardapioDTO cardapioDTO) {
        try {
            CardapioDTO cardapioSalvo = cardapioService.salvarOuAlterar(cardapioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(cardapioSalvo);
        } catch (JaExisteException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()); // ou uma mensagem de erro personalizada
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro desconhecido"); // ou uma mensagem de erro personalizada
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alterarCardapio(@PathVariable Long id, @RequestBody CardapioDTO cardapioDTO) {
        try {
            cardapioDTO.setId(id);
            CardapioDTO cardapioEditado = cardapioService.salvarOuAlterar(cardapioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(cardapioEditado);
        } catch (JaExisteException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()); // ou uma mensagem de erro personalizada
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro desconhecido"); // ou uma mensagem de erro personalizada
        }
    }

    @GetMapping
    public ResponseEntity<List<CardapioDTO>> buscarTodosCardapios() {
        List<CardapioDTO> cardapios = cardapioService.buscarTodos();
        return ResponseEntity.ok(cardapios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardapioDTO> obterCardapio(@PathVariable Long id) {
        CardapioDTO cardapioDTO = cardapioService.obterCardapio(id);
        if (cardapioDTO != null) {
            return ResponseEntity.ok(cardapioDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/por-categoria/{categoriaId}")
    public ResponseEntity<List<CardapioDTO>> buscarPorCategoria(@PathVariable Long categoriaId) {
        Categoria categoria = new Categoria();
        categoria.setId(categoriaId);
        List<CardapioDTO> cardapios = cardapioService.buscarPorCategoria(categoria);
        return ResponseEntity.ok(cardapios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCardapio(@PathVariable Long id) {
        cardapioService.excluirCardapio(id);
        return ResponseEntity.noContent().build();
    }
}
