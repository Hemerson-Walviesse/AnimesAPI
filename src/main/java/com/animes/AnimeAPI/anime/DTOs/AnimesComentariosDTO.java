package com.animes.AnimeAPI.anime.DTOs;

import com.animes.AnimeAPI.comuns.entity.ComentarioDTO;
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
public class AnimesComentariosDTO {
    private AnimeDTO anime;
    private List<ComentarioDTO> comentarios;
}
