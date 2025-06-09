package com.animes.AnimeAPI.comuns.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComentarioDTO {
    private Long id;
    private String texto;
    private boolean ativo = true;

    private LocalDateTime dataCriacao;
}
