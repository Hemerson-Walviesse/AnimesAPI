package com.animes.AnimeAPI.manga.repository;

import com.animes.AnimeAPI.comuns.DTOs.ComentarioDTO;
import com.animes.AnimeAPI.manga.entity.MangaComentarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MangaComentarioRepository extends JpaRepository<MangaComentarioEntity, Long> {
    @Query("SELECT new com.animes.AnimeAPI.comuns.DTOs.ComentarioDTO(c.id, c.texto, c.ativo, c.dataCriacao) FROM MangaComentarioEntity c WHERE c.manga.malId = :malId AND c.ativo = true")
    List<ComentarioDTO> buscarComentariosPorManga(@Param("malId") Integer malId);
    List<ComentarioDTO> findByMangaMalIdAndAtivo(Integer id, boolean ativo);
}
