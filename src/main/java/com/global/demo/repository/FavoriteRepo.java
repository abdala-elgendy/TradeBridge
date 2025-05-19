package com.global.demo.repository;

import com.global.demo.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepo extends JpaRepository<Favorite, Long> {
}
