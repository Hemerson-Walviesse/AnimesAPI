package com.animes.AnimeAPI.anime.DTOs;

import com.animes.AnimeAPI.anime.entity.AnimeComentarioEntity;
import com.animes.AnimeAPI.anime.entity.AnimeEntity;
import com.animes.AnimeAPI.manga.DTOs.MangaDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.animes.AnimeAPI.comuns.DTOs.DTOsComuns;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnimeDTO extends DTOsComuns {

    private Integer episodes;

    public AnimeDTO(AnimeEntity anime) {
        this.setMal_id(anime.getMalId());
        this.setTitle(anime.getTitle());
        DTOsComuns.ImageJpg dtoImageJpg = new DTOsComuns.ImageJpg(anime.getImages().getJpg().getImage_url());
        DTOsComuns.ImagesWapper dtoImages = new DTOsComuns.ImagesWapper(dtoImageJpg);
        this.setImages(dtoImages);
        this.setType(anime.getType());
        this.episodes = anime.getEpisodes();
        this.setScore(anime.getScore());
        this.setSynopsis(anime.getSynopsis());
    }

}
