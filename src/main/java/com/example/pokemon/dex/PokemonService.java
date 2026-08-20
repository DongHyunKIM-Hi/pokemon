package com.example.pokemon.dex;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonService {

    private static final int MIN_LEVEL = 1;
    private static final int MAX_LEVEL = 100;

    private final PokemonRepository pokemonRepository;

    public PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public Pokemon registerPokemon(String name, String type, int level) {
        validateLevel(level);
        return pokemonRepository.save(name, type, level);
    }

    public Pokemon getPokemon(long id) {
        return pokemonRepository.findById(id);
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
        validateLevel(level);
        return pokemonRepository.update(id, type, level);
    }

    public void deletePokemon(long id) {
        pokemonRepository.deleteById(id);
    }

    private void validateLevel(int level) {
        if (level < MIN_LEVEL || level > MAX_LEVEL) {
            throw new IllegalArgumentException(
                    "포켓몬 레벨은 " + MIN_LEVEL + "~" + MAX_LEVEL + " 사이여야 합니다. 입력값: " + level);
        }
    }
}
