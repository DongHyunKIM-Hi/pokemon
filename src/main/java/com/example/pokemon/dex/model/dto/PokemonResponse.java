package com.example.pokemon.dex.model.dto;

import com.example.pokemon.dex.model.entity.Pokemon;

public class PokemonResponse {

    private final long id;
    private final String name;
    private final String type;
    private final int level;

    private PokemonResponse(long id, String name, String type, int level) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.level = level;
    }

    public static PokemonResponse from(Pokemon pokemon) {
        return new PokemonResponse(
                pokemon.getId(), pokemon.getName(), pokemon.getType(), pokemon.getLevel()
        );
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }
}
