package com.example.pokemon.dex;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/v1/pokemons")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @PostMapping
    public String postPokemon(@RequestParam("name") String name,
                               @RequestParam("type") String type,
                               @RequestParam("level") int level,
                               Model model) {
        Pokemon pokemon = pokemonService.registerPokemon(name, type, level);
        model.addAttribute("pokemon", pokemon);
        return "pokemonResult";
    }

    @GetMapping("/{pokemon-id}")
    public String getPokemon(@PathVariable("pokemon-id") long pokemonId, Model model) {
        Pokemon pokemon = pokemonService.getPokemon(pokemonId);
        model.addAttribute("pokemon", pokemon);
        return "pokemonDetail";
    }

    @GetMapping
    public String getPokemons(@RequestParam(value = "type", required = false, defaultValue = "전체") String type,
                               Model model) {
        List<Pokemon> pokemons = pokemonService.getPokemons(type);
        model.addAttribute("pokemons", pokemons);
        model.addAttribute("type", type);
        return "pokemonList";
    }

    @PatchMapping("/{pokemon-id}")
    public String patchPokemon(@PathVariable("pokemon-id") long pokemonId,
                                @RequestParam("type") String type,
                                @RequestParam("level") int level,
                                Model model) {
        Pokemon pokemon = pokemonService.updatePokemon(pokemonId, type, level);
        model.addAttribute("pokemon", pokemon);
        return "pokemonUpdateResult";
    }

    @DeleteMapping("/{pokemon-id}")
    public String deletePokemon(@PathVariable("pokemon-id") long pokemonId, Model model) {
        pokemonService.deletePokemon(pokemonId);
        model.addAttribute("pokemonId", pokemonId);
        return "pokemonDeleteResult";
    }
}
