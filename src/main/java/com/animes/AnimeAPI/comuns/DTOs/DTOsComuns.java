package com.animes.AnimeAPI.comuns.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class DTOsComuns {
    private Integer mal_id;
    private String title;
    private String type;
    private String synopsis;
    private Double score;
    private ImagesWapper images;



    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ImagesWapper {
        private ImageJpg jpg;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ImageJpg {
        private String image_url;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ComentarioDTO {

        private Integer id;
        private String texto;
        private boolean ativo = true;

        private LocalDateTime dataCriacao;

        public ComentarioDTO(Integer id, String texto, boolean ativo, LocalDateTime dataCriacao) {
            this.id = id;
            this.texto = texto;
            this.ativo = ativo;
            this.dataCriacao = dataCriacao;
        }
    }
}
