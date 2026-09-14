package com.example.pokemon.dex.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CreatePokemonRequest {

    @Schema(description = "포켓몬 이름", example = "피카츄")
    @NotBlank(message = "이름은 필수입니다")
    private String name;

    @Schema(description = "타입", example = "electric")
    @NotBlank(message = "타입은 필수입니다")
    private String type;

    @Schema(description = "레벨 (1~100)", example = "5")
    @Min(value = 1, message = "레벨은 1 이상이어야 합니다")
    @Max(value = 100, message = "레벨은 100을 넘을 수 없습니다")
    private int level;

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
