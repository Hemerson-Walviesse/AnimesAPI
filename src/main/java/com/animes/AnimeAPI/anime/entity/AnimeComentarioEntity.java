package com.animes.AnimeAPI.anime.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comentario_anime")
public class AnimeComentarioEntity extends com.animes.AnimeAPI.comuns.entity.ComentarioEntity {
    @ManyToOne
    @JoinColumn(name = "anime_id")
    @JsonBackReference
    private AnimeEntity anime;

}

