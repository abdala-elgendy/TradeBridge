// src/main/java/com/global/demo/controller/FavoriteController.java
package com.global.demo.controller;

import com.global.demo.entity.Favorite;
import com.global.demo.entity.User;
import com.global.demo.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/add")
    public Favorite addFavorite(@AuthenticationPrincipal User user, @RequestBody Favorite favorite) {
        return favoriteService.addFavorite(favorite);
    }

    @GetMapping("/allmyfavorites")
    public List<Favorite> getAllFavorites(@AuthenticationPrincipal User user) {
        return favoriteService.getAllFavorites();
    }

    @GetMapping("/{id}")
    public Favorite getFavoriteById(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return favoriteService.getFavoriteById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteFavorite(@AuthenticationPrincipal User user, @PathVariable Long id) {
        favoriteService.deleteFavorite(id);
    }
}