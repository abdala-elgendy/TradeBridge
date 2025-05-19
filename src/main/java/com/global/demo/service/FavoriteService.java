// src/main/java/com/global/demo/service/FavoriteService.java
package com.global.demo.service;

import com.global.demo.entity.Favorite;
import com.global.demo.repository.FavoriteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepo favoriteRepository;

    public Favorite addFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findAll();
    }

    public Favorite getFavoriteById(Long id) {
        Optional<Favorite> favorite = favoriteRepository.findById(id);
        return favorite.orElse(null);
    }

    public void deleteFavorite(Long id) {
        favoriteRepository.deleteById(id);
    }
}