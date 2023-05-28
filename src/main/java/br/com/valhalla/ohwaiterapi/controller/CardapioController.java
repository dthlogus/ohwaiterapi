package br.com.valhalla.ohwaiterapi.controller;

import br.com.valhalla.ohwaiterapi.dto.CardapioDTO;
import br.com.valhalla.ohwaiterapi.service.CardapioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapios")
public class CardapioController {

    private final CardapioService cardapioService;

    public CardapioController(CardapioService cardapioService) {
        this.cardapioService = cardapioService;
    }

    @GetMapping
    public ResponseEntity<List<CardapioDTO>> listarCardapios() {
        List<CardapioDTO> cardapios = cardapioService.buscarTodos();
        return ResponseEntity.ok(cardapios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardapioDTO> obterCardapio(@PathVariable Long id) {
        CardapioDTO cardapio = cardapioService.obterCardapio(id);
        if (cardapio != null) {
            return ResponseEntity.ok(cardapio);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CardapioDTO> adicionarCardapio(@RequestBody CardapioDTO cardapioDTO) {
        CardapioDTO novoCardapio = cardapioService.salvarOuAlterar(cardapioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCardapio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCardapio(@PathVariable Long id) {
        cardapioService.excluirCardapio(id);
        return ResponseEntity.noContent().build();
    }
}
