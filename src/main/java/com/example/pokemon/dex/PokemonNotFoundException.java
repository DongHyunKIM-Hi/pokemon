package com.example.pokemon.dex;

public class PokemonNotFoundException extends RuntimeException {

    public PokemonNotFoundException(long id) {
        super("해당 포켓몬을 찾을 수 없습니다. id=" + id);
    }
}
