package com.example.pokemon.dex;

import com.example.pokemon.common.ApiResponse;
import com.example.pokemon.dex.dto.CreatePokemonRequest;
import com.example.pokemon.dex.dto.PokemonResponse;
import com.example.pokemon.dex.dto.UpdatePokemonRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/pokemons")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PokemonResponse>> createPokemon(
            @Valid @RequestBody CreatePokemonRequest request) {

        Pokemon pokemon = pokemonService.registerPokemon(
                request.getName(), request.getType(), request.getLevel());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(PokemonResponse.from(pokemon)));
    }

    @GetMapping("/{pokemon-id}")
    public ResponseEntity<ApiResponse<PokemonResponse>> getPokemon(
            @PathVariable("pokemon-id") long pokemonId) {

        Pokemon pokemon = pokemonService.getPokemon(pokemonId);
        return ResponseEntity.ok(ApiResponse.success(PokemonResponse.from(pokemon)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PokemonResponse>>> getPokemons(
            @RequestParam(value = "type", required = false) String type) {

        List<PokemonResponse> pokemons = pokemonService.getPokemons(type).stream()
                .map(PokemonResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(pokemons));
    }

    @PatchMapping("/{pokemon-id}")
    public ResponseEntity<ApiResponse<PokemonResponse>> updatePokemon(
            @PathVariable("pokemon-id") long pokemonId,
            @Valid @RequestBody UpdatePokemonRequest request) {

        Pokemon pokemon = pokemonService.updatePokemon(
                pokemonId, request.getType(), request.getLevel());

        return ResponseEntity.ok(ApiResponse.success(PokemonResponse.from(pokemon)));
    }

    @DeleteMapping("/{pokemon-id}")
    public ResponseEntity<Void> deletePokemon(@PathVariable("pokemon-id") long pokemonId) {
        pokemonService.deletePokemon(pokemonId);
        return ResponseEntity.noContent().build();
    }
}
