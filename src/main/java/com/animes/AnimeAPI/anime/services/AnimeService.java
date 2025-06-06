package com.animes.AnimeAPI.anime.services;
import com.animes.AnimeAPI.anime.DTOs.AnimeDTO;
import com.animes.AnimeAPI.anime.DTOs.WrapperListDTO;
import com.animes.AnimeAPI.anime.client.AnimeClient;
import com.animes.AnimeAPI.anime.entity.AnimeComentarioEntity;
import com.animes.AnimeAPI.anime.entity.AnimeEntity;
import com.animes.AnimeAPI.anime.repository.AnimeComentarioRepository;
import com.animes.AnimeAPI.anime.repository.AnimeRepository;
import com.animes.AnimeAPI.comuns.entity.ComentarioEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimeService {

    @Autowired
    AnimeClient animeClient;

    @Autowired
    AnimeRepository animeRepository;
    @Autowired
    AnimeComentarioRepository animeComentarioRepository;

    public AnimeEntity salvarAnimeSeNaoExistir(AnimeDTO dto){
        return animeRepository.findById(dto.getMal_id())
                .orElseGet(() -> {
                    AnimeEntity anime = new AnimeEntity();
                    anime.setMalId(dto.getMal_id());
                    anime.setTitle(dto.getTitle());
                    anime.setEpisodes(dto.getEpisodes());
                    anime.setScore(dto.getScore() != null ? dto.getScore().doubleValue() : null);
                    anime.setType(dto.getType());
                    anime.setSynopsis(dto.getSynopsis());

                    AnimeEntity.ImagesWapper imagesWapper = new AnimeEntity.ImagesWapper();
                    AnimeEntity.ImageJpg imageJpg = new AnimeEntity.ImageJpg();
                    imageJpg.setImage_url(dto.getImages().getJpg().getImage_url());
                    imagesWapper.setJpg(imageJpg);
                    anime.setImages(imagesWapper);

                    return animeRepository.save(anime);
                });
    }

    public <T> T getAnimeByIdTratado(String endpoint, Class<T> clazz) throws Exception {

        String jsonResponse = animeClient.chamarApiExternamente(endpoint);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonResponse);

        if (root.has("status")) {
            throw new RuntimeException("Erro: " + root.get("status").asText());
        }
       return mapper.readValue(jsonResponse, clazz);


    }
    public List<AnimeEntity> buscarEManterAnimes(String endpoint) {
        try {
            WrapperListDTO wrapperListDTO = getAnimeByIdTratado(endpoint, WrapperListDTO.class);
            return wrapperListDTO.getData().stream()
                    .map(this::salvarAnimeSeNaoExistir)
                    .collect(Collectors.toList());
        }catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar JSON: " + e.getMessage(), e);
        }catch (Exception e){
            throw new RuntimeException("Erro ao processar JSON: " + e.getMessage(), e);
        }
        }

        public ComentarioEntity excluirComentario(AnimeComentarioEntity comentario) {

        comentario.setAtivo(false);
        return animeComentarioRepository.save(comentario);

        }
}
