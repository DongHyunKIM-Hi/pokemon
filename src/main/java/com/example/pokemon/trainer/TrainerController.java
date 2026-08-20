package com.example.pokemon.trainer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/v1/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    public String postTrainer(@RequestParam("email") String email,
                               @RequestParam("name") String name,
                               @RequestParam("region") String region,
                               Model model) {
        Trainer trainer = trainerService.registerTrainer(email, name, region);
        model.addAttribute("email", trainer.getEmail());
        model.addAttribute("name", trainer.getName());
        model.addAttribute("region", trainer.getRegion());
        return "trainerResult";
    }

    @GetMapping("/{trainer-id}")
    public String getTrainer(@PathVariable("trainer-id") long trainerId, Model model) {
        Trainer trainer = trainerService.getTrainer(trainerId);
        model.addAttribute("trainer", trainer);
        return "trainerDetail";
    }

    @GetMapping
    public String getTrainers(Model model) {
        List<Trainer> trainers = trainerService.getTrainers();
        model.addAttribute("trainers", trainers);
        return "trainerList";
    }
}
