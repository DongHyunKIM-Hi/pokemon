package com.example.pokemon.dex.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class UpdatePokemonRequest {

    private String type;

    private int level;

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }
}
