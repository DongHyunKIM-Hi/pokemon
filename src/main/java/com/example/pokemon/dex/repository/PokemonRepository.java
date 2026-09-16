package com.example.pokemon.dex.repository;

import com.example.pokemon.dex.model.entity.Pokemon;
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

        String updateType;
        int updateLevel;

        // type 외부에서 받아온 값 -> 업데이트하려고하는 type
        // 값이 있는지 없는지 검사
        // 값이 있다. -> 타입을 변경하고 싶어함.
        // 값이 없다. -> 타입을 변경하고 싶어하지 않음.

        if (type == null) {
            // 값을 변경하고 싶어하지 않음.
            updateType = existing.getType();
        } else {
            // 값이 존재해 변경하고 싶은 것임
            updateType = type;
        }

        if (level == 0) {
            updateLevel = existing.getLevel();
        } else {
            updateLevel = level;
        }




        Pokemon updated = new Pokemon(id, existing.getName(), updateType, updateLevel);
        store.put(id, updated);
        return updated;
    }

    public void deleteById(long id) {
        store.remove(id);
    }
}
