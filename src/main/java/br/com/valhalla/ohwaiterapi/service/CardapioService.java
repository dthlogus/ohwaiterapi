package br.com.valhalla.ohwaiterapi.service;

import br.com.valhalla.ohwaiterapi.dto.CardapioDTO;
import br.com.valhalla.ohwaiterapi.entity.Cardapio;
import br.com.valhalla.ohwaiterapi.entity.Categoria;
import br.com.valhalla.ohwaiterapi.repository.CardapioRepository;
import br.com.valhalla.ohwaiterapi.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardapioService {

    private final CardapioRepository cardapioRepository;

    public CardapioService(CardapioRepository cardapioRepository) {
        this.cardapioRepository = cardapioRepository;
    }

    public CardapioDTO salvarOuAlterar(CardapioDTO cardapioDTO) {
        Cardapio cardapio = CardapioDTO.toEntity(cardapioDTO);
        Cardapio cardapioSalvo = cardapioRepository.save(cardapio);
        return CardapioDTO.toDto(cardapioSalvo);
    }

    public List<CardapioDTO> buscarTodos() {
        List<Cardapio> cardapios = cardapioRepository.findAll();
        return cardapios.stream()
                .map(CardapioDTO::toDto)
                .collect(Collectors.toList());
    }

    public List<CardapioDTO> buscarPorCategoria(Categoria categoria) {
        List<Cardapio> cardapios = cardapioRepository.findByCategoria(categoria);
        return cardapios.stream()
                .map(CardapioDTO::toDto)
                .collect(Collectors.toList());
    }

    public CardapioDTO obterCardapio(Long id) {
        Optional<Cardapio> cardapioOptional = cardapioRepository.findById(id);
        return cardapioOptional.map(CardapioDTO::toDto).orElse(null);
    }

    public void excluirCardapio(Long id) {
        cardapioRepository.deleteById(id);
    }
}