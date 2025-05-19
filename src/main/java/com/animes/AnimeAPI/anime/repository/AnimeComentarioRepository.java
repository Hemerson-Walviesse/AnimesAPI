package com.animes.AnimeAPI.anime.repository;

import com.animes.AnimeAPI.anime.entity.AnimeComentarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeComentarioRepository extends JpaRepository<AnimeComentarioEntity, Integer> {
}
