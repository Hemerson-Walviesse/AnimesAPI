package com.animes.AnimeAPI.manga.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "manga")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MangaEntity{

    @Id
    @Column(name = "mal_id")
    private Integer malId;
    private Integer chapters;
    private String title;
    private String synopsis;
    private String type;
    private Double score;
    @Embedded
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
    @Embeddable
    public static class ImageJpg {
        @Column(name = "image_url")
        private String image_url;
    }
    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<MangaComentarioEntity> comentarios = new ArrayList<>();


}
