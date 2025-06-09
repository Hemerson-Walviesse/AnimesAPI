package com.animes.AnimeAPI.anime.entity;
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
@Table(name = "anime")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnimeEntity{

    @Id

    @Column(name = "mal_id") // mantém o nome da coluna no BD
    private Integer malId;
    private String title;
    private String synopsis;
    private Integer episodes;
    private String type;
    private Double score;
    @Embedded
    private ImagesWapper images;

    public Integer getMalId() {
        return malId;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Embeddable
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

    @OneToMany(mappedBy = "anime", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<AnimeComentarioEntity> comentarios;


}
