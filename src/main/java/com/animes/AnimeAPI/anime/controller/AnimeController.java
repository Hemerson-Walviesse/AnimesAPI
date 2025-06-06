package com.animes.AnimeAPI.anime.controller;
import com.animes.AnimeAPI.anime.DTOs.AnimeDTO;
import com.animes.AnimeAPI.anime.DTOs.AnimesComentariosDTO;
import com.animes.AnimeAPI.anime.DTOs.WrapperDTO;
import com.animes.AnimeAPI.anime.DTOs.WrapperListDTO;
import com.animes.AnimeAPI.anime.entity.AnimeEntity;
import com.animes.AnimeAPI.anime.entity.AnimeComentarioEntity;
import com.animes.AnimeAPI.anime.repository.AnimeRepository;
import com.animes.AnimeAPI.anime.repository.AnimeComentarioRepository;
import com.animes.AnimeAPI.anime.services.AnimeService;
import com.animes.AnimeAPI.comuns.entity.ComentarioDTO;
import com.animes.AnimeAPI.comuns.entity.ComentarioEntity;
import com.animes.AnimeAPI.comuns.services.ServiceComum;
import com.animes.AnimeAPI.manga.DTOs.MangaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Animes", description = "Operações relacionadas aos animes.")
public class AnimeController {

    @Autowired
    private AnimeService animeService;
    @Autowired
    private ServiceComum serviceComum;
    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    AnimeComentarioRepository comentarioRepository;

    @GetMapping("/anime/{id}")
    @Operation(summary = "Busca os animes gerais ou algum especifico pelo ID.", description = "Retorna todos os animes do banco de dados")
    public ResponseEntity<?> getAnimeById(@PathVariable("id") String id) {

        try{
         WrapperDTO wrapper = animeService.getAnimeByIdTratado("/" + id, WrapperDTO.class);
         System.out.println(wrapper);
         AnimeEntity anime = animeService.salvarAnimeSeNaoExistir(wrapper.getData());
            AnimeDTO resposta = new AnimeDTO(anime);
            return ResponseEntity.ok(resposta);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");
        }
    }

    @GetMapping("/anime")
    @Operation(summary = "Busca um anime pelo nome ou pela pagina", description = "Retorna os animes pesquisados com o filtro.")
    public ResponseEntity<?> getAnimeComFiltros(@RequestParam(value = "name",required = false) String name,@RequestParam(value = "page", required = false) String page){
        try{
            String endpoint = serviceComum.montarEndpoint(name, page);
            List<AnimeEntity> salvos = animeService.buscarEManterAnimes(endpoint);
            List<AnimeDTO> resposta = salvos.stream()
                    .map(AnimeDTO::new)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(resposta);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/anime/salvos")
    @Operation(summary = "Salva novos animes.", description = "Salva um novo anime ao banco de dados se ainda não existir.")
    public ResponseEntity<?> getAnimeSalvar(@RequestParam(value = "id", required = false) String id,
                                            @RequestParam(value = "name",required = false) String name,
                                            @RequestParam(value = "page", required = false) String page){
        try{

            if(id != null){

                WrapperDTO wrapper = animeService.getAnimeByIdTratado("/" + id, WrapperDTO.class);
                AnimeEntity animeSalvo = animeService.salvarAnimeSeNaoExistir(wrapper.getData());
                animeRepository.save(animeSalvo);
                List<ComentarioDTO> comentarios = comentarioRepository.findByAnimeMalIdAndAtivo(animeSalvo.getMalId(), true);

                // Cria resposta com anime e comentários
                AnimesComentariosDTO response = new AnimesComentariosDTO(new AnimeDTO(animeSalvo), comentarios);
                return ResponseEntity.ok(response);

            }else{
                String endpoint = serviceComum.montarEndpoint(name, page);
                WrapperListDTO wrapperListDTO = animeService.getAnimeByIdTratado(endpoint.toString(), WrapperListDTO.class);
                List<AnimeEntity> salvos = wrapperListDTO.getData().stream()
                        .map(animeService::salvarAnimeSeNaoExistir)
                        .collect(Collectors.toList());
                List<AnimeDTO> resposta = salvos.stream()
                        .map(AnimeDTO::new)
                        .collect(Collectors.toList());

                return ResponseEntity.ok(resposta);
            }

        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/anime/{id}/comentarios")
    @Operation(summary = "Permite comentar sobre o anime.", description = "Permite comentarios sobre o anime pelo ID.")
    public ResponseEntity<?> adicionarcomentario(@PathVariable Integer id, @RequestBody AnimeComentarioEntity comentario) {
        try {
            Optional<AnimeEntity> comentarioAnime = animeRepository.findById(id);
            if (comentarioAnime.isPresent()) {
                AnimeEntity anime = comentarioAnime.get();
                comentario.setAnime(anime);
                comentario.setId(null);
                comentario.setDataCriacao(LocalDateTime.now());
                animeRepository.save(anime);
                comentarioRepository.save(comentario);
                return ResponseEntity.status(HttpStatus.CREATED).body(comentario);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anime com ID " + id + " não encontrado.");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao adicionar comentário: " + e.getMessage());
        }
    }

    @DeleteMapping("/anime/{animeId}/comentarios/{comentarioId}")
    public ResponseEntity<?> deletaComentario(@PathVariable Integer animeId, @PathVariable Integer comentarioId) {
        Optional<AnimeEntity> anime = animeRepository.findById(animeId);
        if (anime.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<AnimeComentarioEntity> comentario = comentarioRepository.findById(comentarioId);
        if (comentario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        animeService.excluirComentario(comentario.get());

        return ResponseEntity.ok().build();
    }
}

