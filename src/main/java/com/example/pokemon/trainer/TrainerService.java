package com.example.pokemon.trainer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public Trainer registerTrainer(String email, String name, String region) {
        return trainerRepository.save(email, name, region);
    }

    public Trainer getTrainer(long id) {
        return trainerRepository.findById(id);
    }

    public List<Trainer> getTrainers() {
        return trainerRepository.findAll();
    }
}
