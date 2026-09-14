package com.example.pokemon.dex.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Pokemon {
    private long id;
    private String name;
    private String type;
    private int level;
}
