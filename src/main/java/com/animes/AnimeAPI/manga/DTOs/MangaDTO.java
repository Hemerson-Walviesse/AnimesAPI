package com.animes.AnimeAPI.manga.DTOs;


import com.animes.AnimeAPI.anime.entity.AnimeEntity;
import com.animes.AnimeAPI.manga.entity.MangaEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MangaDTO {
    private Integer mal_id;
    private String title;
    private String type;
    private Integer chapters;
    private String synopsis;
    private Double score;
    private ImagensWapper images;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ImagensWapper{
        private ImageJpg jpg;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ImageJpg{
        private String image_url;
    }

    public MangaDTO(MangaEntity manga) {
        this.setMal_id(manga.getMalId());
        this.setTitle(manga.getTitle());
        MangaDTO.ImageJpg dtoImageJpg = new MangaDTO.ImageJpg(manga.getImages().getJpg().getImage_url());
        MangaDTO.ImagensWapper dtoImages = new MangaDTO.ImagensWapper(dtoImageJpg);
        this.setImages(dtoImages);
        this.setType(manga.getType());
        this.synopsis = manga.getSynopsis();
        this.setChapters(manga.getChapters());
        this.setScore(manga.getScore());
        this.setSynopsis(manga.getSynopsis());
    }
}
