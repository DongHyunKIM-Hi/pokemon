package com.example.pokemon.dex.controller;

import com.example.pokemon.common.dto.ApiResponse;
import com.example.pokemon.dex.model.entity.Pokemon;
import com.example.pokemon.dex.service.PokemonService;
import com.example.pokemon.dex.model.dto.CreatePokemonRequest;
import com.example.pokemon.dex.model.dto.PokemonResponse;
import com.example.pokemon.dex.model.dto.UpdatePokemonRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "포켓몬", description = "포켓몬 도감 관리 API")
@RestController
@RequestMapping("/v1/pokemons")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @Operation(summary = "포켓몬 등록", description = "새 포켓몬을 도감에 등록한다")
    @PostMapping
    public ResponseEntity<ApiResponse<PokemonResponse>> createPokemon(
            @Valid @RequestBody CreatePokemonRequest request) {

        Pokemon pokemon = pokemonService.registerPokemon(
                request.getName(), request.getType(), request.getLevel());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(PokemonResponse.from(pokemon)));
    }

    @Operation(summary = "단건 조회", description = "id로 포켓몬 한 마리를 조회한다")
    @GetMapping("/{pokemon-id}")
    public ResponseEntity<ApiResponse<PokemonResponse>> getPokemon(
            @PathVariable("pokemon-id") long pokemonId) {

        Pokemon pokemon = pokemonService.getPokemon(pokemonId);
        return ResponseEntity.ok(ApiResponse.success(PokemonResponse.from(pokemon)));
    }

    @Operation(summary = "목록 조회", description = "전체 또는 타입으로 필터링된 포켓몬 목록을 조회한다")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PokemonResponse>>> getPokemons(
            @RequestParam(value = "type", required = false) String type) {

        List<PokemonResponse> pokemons = pokemonService.getPokemons(type).stream()
                .map(PokemonResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(pokemons));
    }

    @Operation(summary = "수정", description = "타입과 레벨을 수정한다")
    @PatchMapping("/{pokemon-id}")
    public ResponseEntity<ApiResponse<PokemonResponse>> updatePokemon(
            @PathVariable("pokemon-id") long pokemonId,
            @Valid @RequestBody UpdatePokemonRequest request) {

        Pokemon pokemon = pokemonService.updatePokemon(
                pokemonId, request.getType(), request.getLevel());

        return ResponseEntity.ok(ApiResponse.success(PokemonResponse.from(pokemon)));
    }

    @Operation(summary = "삭제", description = "id로 포켓몬을 삭제한다")
    @DeleteMapping("/{pokemon-id}")
    public ResponseEntity<Void> deletePokemon(@PathVariable("pokemon-id") long pokemonId) {
        pokemonService.deletePokemon(pokemonId);
        return ResponseEntity.noContent().build();
    }
}
