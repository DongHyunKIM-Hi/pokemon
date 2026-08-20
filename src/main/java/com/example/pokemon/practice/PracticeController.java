package com.example.pokemon.practice;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/v1/practice")
public class PracticeController {

    @GetMapping("/greet")
    public String greet(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "practiceGreet";
    }

    @GetMapping("/pokemon-info")
    public String pokemonInfo(@RequestParam("name") String name,
                               @RequestParam("level") int level,
                               Model model) {
        model.addAttribute("name", name);
        model.addAttribute("level", level);
        return "practicePokemonInfo";
    }

    @GetMapping("/pokemon/{pokemon-id}")
    public String pokemonPath(@PathVariable("pokemon-id") long pokemonId, Model model) {
        model.addAttribute("pokemonId", pokemonId);
        return "practicePokemonPath";
    }

    @GetMapping("/pokemon/{pokemon-id}/moves")
    public String pokemonMoves(@PathVariable("pokemon-id") long pokemonId,
                                @RequestParam("type") String type,
                                Model model) {
        model.addAttribute("pokemonId", pokemonId);
        model.addAttribute("type", type);
        return "practicePokemonMoves";
    }

    @GetMapping("/pokemon-search")
    public String pokemonSearch(@RequestParam(value = "region", required = false, defaultValue = "전체") String region,
                                 Model model) {
        model.addAttribute("region", region);
        return "practicePokemonSearch";
    }
}
