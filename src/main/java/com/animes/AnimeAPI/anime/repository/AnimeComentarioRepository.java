package com.animes.AnimeAPI.anime.repository;

import com.animes.AnimeAPI.anime.entity.AnimeComentarioEntity;

import com.animes.AnimeAPI.comuns.DTOs.ComentarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimeComentarioRepository extends JpaRepository<AnimeComentarioEntity, Integer> {
   @Query("SELECT new com.animes.AnimeAPI.comuns.DTOs.ComentarioDTO(c.id, c.texto, c.ativo, c.dataCriacao) FROM AnimeComentarioEntity c WHERE c.anime.malId = :malId AND c.ativo = true")
   List<ComentarioDTO> buscarComentariosPorAnime(@Param("malId") Integer malId);
   List<ComentarioDTO> findByAnimeMalIdAndAtivo(Integer id, boolean ativo);

}
