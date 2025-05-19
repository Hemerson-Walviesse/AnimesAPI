package com.animes.AnimeAPI.comuns.DTOs;

import com.animes.AnimeAPI.anime.entity.AnimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
}
