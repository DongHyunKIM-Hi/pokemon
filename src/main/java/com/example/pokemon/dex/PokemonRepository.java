package com.example.pokemon.dex;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PokemonRepository {

    private final Map<Long, Pokemon> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Pokemon save(String name, String type, int level) {
        long id = idGenerator.getAndIncrement();
        Pokemon pokemon = new Pokemon(id, name, type, level);
        store.put(id, pokemon);
        return pokemon;
    }

    public Pokemon findById(long id) {
        return store.get(id);
    }

    public List<Pokemon> findAll() {
        return new ArrayList<>(store.values());
    }

    public Pokemon update(long id, String type, int level) {
        Pokemon existing = store.get(id);
        if (existing == null) {
            return null;
        }
        Pokemon updated = new Pokemon(id, existing.getName(), type, level);
        store.put(id, updated);
        return updated;
    }

    public void deleteById(long id) {
        store.remove(id);
    }
}
