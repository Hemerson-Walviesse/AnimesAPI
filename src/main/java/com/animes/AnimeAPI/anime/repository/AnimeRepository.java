package com.animes.AnimeAPI.anime.repository;

import com.animes.AnimeAPI.anime.entity.AnimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnimeRepository extends JpaRepository<AnimeEntity, Integer> {

}
