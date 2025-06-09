package com.animes.AnimeAPI.manga.DTOs;

import com.animes.AnimeAPI.comuns.DTOs.ComentarioDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MangasComentarioDTO {
    private MangaDTO manga;
    private List<ComentarioDTO> comentarios;
}
