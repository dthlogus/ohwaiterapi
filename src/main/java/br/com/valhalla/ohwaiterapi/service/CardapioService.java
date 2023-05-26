package br.com.valhalla.ohwaiterapi.service;

import br.com.valhalla.ohwaiterapi.entity.Cardapio;
import br.com.valhalla.ohwaiterapi.repository.CardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardapioService {

    @Autowired
    private CardapioRepository repository;

    public Cardapio salvarOuAlterarCardapio(Cardapio cardapio){
        return repository.save(cardapio);
    }

    public List<Cardapio> buscarTodosCardapio(){
        return repository.findAll();
    }

    public void excluirCardapio(Cardapio cardapio){
        repository.delete(cardapio);
    }

}
