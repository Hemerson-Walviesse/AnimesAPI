package com.animes.AnimeAPI.manga.services;

import com.animes.AnimeAPI.manga.DTOs.MangaDTO;
import com.animes.AnimeAPI.manga.DTOs.WrapperListDTO;
import com.animes.AnimeAPI.manga.client.MangaClient;
import com.animes.AnimeAPI.manga.entity.MangaEntity;
import com.animes.AnimeAPI.manga.repository.MangaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MangaService {

    @Autowired
    MangaClient mangaClient;

    @Autowired
    MangaRepository mangaRepository;

    public <T> T getMangaByIdTratado(String endpoint, Class<T> clazz) throws JsonProcessingException {
        String jsonResponse = mangaClient.chamarApiExternamente(endpoint);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonResponse);

        if(root.has("status")){
            throw new RuntimeException("Erro: " + root.get("status").asText());
        }
        return mapper.convertValue(root, clazz);
    }

    public MangaEntity salvarMangaSeNaoExiste(MangaDTO dto){
        return mangaRepository.findById(dto.getMal_id())
                .orElseGet(()-> {
                    MangaEntity manga = new MangaEntity();
                    manga.setMal_id(dto.getMal_id());
                    manga.setTitle(dto.getTitle());
                    manga.setChapters(dto.getChapters());
                    manga.setScore(dto.getScore() != null ? dto.getScore().doubleValue(): null);
                    manga.setType(dto.getType());
                    manga.setSynopsis(dto.getSynopsis());

                    MangaEntity.ImagesWapper imagesWapper = new MangaEntity.ImagesWapper();
                    MangaEntity.ImageJpg imageJpg = new MangaEntity.ImageJpg();
                    imageJpg.setImage_url(dto.getImages().getJpg().getImage_url());
                    imagesWapper.setJpg(imageJpg);
                    manga.setImages(imagesWapper);
                    return mangaRepository.save(manga);
                });


    }

    public List<MangaEntity> buscarEManterMangas(String endpoint) {
        try {
            WrapperListDTO wrapperListDTO = getMangaByIdTratado(endpoint, WrapperListDTO.class);
            return wrapperListDTO.getData().stream()
                    .map(this::salvarMangaSeNaoExiste)
                    .collect(Collectors.toList());
        }catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar JSON: " + e.getMessage(), e);
        }
    }

}
