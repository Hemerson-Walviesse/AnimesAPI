package com.animes.AnimeAPI.manga.repository;

import com.animes.AnimeAPI.anime.entity.AnimeEntity;
import com.animes.AnimeAPI.manga.entity.MangaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MangaRepository extends JpaRepository<MangaEntity, Integer> {

}
