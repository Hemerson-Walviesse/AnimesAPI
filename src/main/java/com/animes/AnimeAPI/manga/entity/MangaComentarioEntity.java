package com.animes.AnimeAPI.manga.entity;
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
@Table(name = "comentario_manga")
public class MangaComentarioEntity extends com.animes.AnimeAPI.comuns.entity.ComentarioEntity {
    @ManyToOne
    @JoinColumn(name = "manga_id")
    @JsonBackReference
    private MangaEntity manga;
}
