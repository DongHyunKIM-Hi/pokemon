package com.example.pokemon.trainer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Trainer {
    private long id;
    private String email;
    private String name;
    private String region;
}
