package com.animes.AnimeAPI.anime.repository;

import com.animes.AnimeAPI.anime.entity.AnimeComentarioEntity;
import com.animes.AnimeAPI.comuns.entity.ComentarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeComentarioRepository extends JpaRepository<AnimeComentarioEntity, Integer> {
   List<ComentarioDTO> findByAnimeMalIdAndAtivo(Integer id, boolean ativo);
}
