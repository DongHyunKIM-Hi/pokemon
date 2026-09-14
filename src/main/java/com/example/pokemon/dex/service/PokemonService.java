package com.example.pokemon.dex.service;

import com.example.pokemon.common.exception.PokemonNotFoundException;
import com.example.pokemon.dex.model.entity.Pokemon;
import com.example.pokemon.dex.repository.PokemonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;

    public PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public Pokemon registerPokemon(String name, String type, int level) {
        return pokemonRepository.save(name, type, level);
    }

    public Pokemon getPokemon(long id) {
        Pokemon pokemon = pokemonRepository.findById(id);
        if (pokemon == null) {
            throw new PokemonNotFoundException(id);
        }
        return pokemon;
    }

    public List<Pokemon> getPokemons(String type) {
        List<Pokemon> pokemons = pokemonRepository.findAll();
        if (type == null || type.equals("전체")) {
            return pokemons;
        }
        return pokemons.stream()
                .filter(pokemon -> pokemon.getType().equals(type))
                .collect(Collectors.toList());
    }

    public Pokemon updatePokemon(long id, String type, int level) {
        Pokemon updated = pokemonRepository.update(id, type, level);
        if (updated == null) {
            throw new PokemonNotFoundException(id);
        }
        return updated;
    }

    public void deletePokemon(long id) {
        pokemonRepository.deleteById(id);
    }
}
