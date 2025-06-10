package com.animes.AnimeAPI.manga.controller;
import com.animes.AnimeAPI.comuns.DTOs.ComentarioDTO;
import com.animes.AnimeAPI.comuns.services.ServiceComum;
import com.animes.AnimeAPI.manga.DTOs.MangaDTO;
import com.animes.AnimeAPI.manga.DTOs.MangasComentarioDTO;
import com.animes.AnimeAPI.manga.DTOs.WrapperDTO;
import com.animes.AnimeAPI.manga.DTOs.WrapperListDTO;
import com.animes.AnimeAPI.manga.entity.MangaComentarioEntity;
import com.animes.AnimeAPI.manga.entity.MangaEntity;
import com.animes.AnimeAPI.manga.repository.MangaComentarioRepository;
import com.animes.AnimeAPI.manga.repository.MangaRepository;
import com.animes.AnimeAPI.manga.services.MangaService;
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
@Tag(name = "Mangas", description = "Operações relacionadas aos mangas.")
public class MangaController {

    @Autowired
    private MangaService mangaService;
    @Autowired
    private MangaRepository mangaRepository;
    @Autowired
    private MangaComentarioRepository comentarioRepository;
    @Autowired
    private ServiceComum serviceComum;

    @GetMapping("/manga/{id}")
    @Operation(summary = "Busca os mangas gerais ou algum especifico pelo ID.", description = "Retorna todos os manga do banco de dados")
    public ResponseEntity<?> getMangaId(@PathVariable("id") String id) {
        try {
            WrapperDTO wrapperDTO = mangaService.getMangaByIdTratado("/" + id, WrapperDTO.class);
            MangaEntity manga = mangaService.salvarMangaSeNaoExiste(wrapperDTO.getData());
            List<ComentarioDTO> comentarios = comentarioRepository.buscarComentariosPorManga(manga.getMalId());
            MangasComentarioDTO resposta = new MangasComentarioDTO(new MangaDTO(manga), comentarios);

            return ResponseEntity.ok(resposta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/manga")
    @Operation(summary = "Busca um manga pelo nome ou pela pagina", description = "Retorna os manga pesquisados com o filtro.")
    public ResponseEntity<?> getMangaComFiltros(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "page", required = false) String page) {
        try {
            String endpoint = serviceComum.montarEndpoint(name, page);
            List<MangaEntity> salvos = mangaService.buscarEManterMangas(endpoint);
            List<MangaDTO> resposta = salvos.stream()
                    .map(MangaDTO::new)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(resposta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("manga/salvos")
    @Operation(summary = "Salva novos mangas.", description = "Salva um novo manga ao banco de dados se ainda não existir.")
    public ResponseEntity<?> getMangaSalvos(@RequestParam(value = "id", required = false) String id,
                                            @RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "page", required = false) String page) {
        try {
            if (id != null) {
                WrapperDTO wrapperDTO = mangaService.getMangaByIdTratado("/" + id, WrapperDTO.class);
                MangaEntity manga = mangaService.salvarMangaSeNaoExiste(wrapperDTO.getData());
                MangaDTO response = new MangaDTO(manga);
                return ResponseEntity.ok(response);
            } else {
                String endpoint = serviceComum.montarEndpoint(name, page);
                WrapperListDTO wrapperListDTO = mangaService.getMangaByIdTratado(endpoint.toString(), WrapperListDTO.class);
                List<MangaEntity> salvos = wrapperListDTO.getData().stream()
                        .map(mangaService::salvarMangaSeNaoExiste)
                        .collect(Collectors.toList());
                List<MangaDTO> resposta = salvos.stream()
                        .map(MangaDTO::new)
                        .collect(Collectors.toList());

                return ResponseEntity.ok(resposta);
            }

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/manga/{id}/comentarios")
    @Operation(summary = "Permite comentar sobre o manga.", description = "Permite comentarios sobre o manga pelo ID.")
    public ResponseEntity<?> adicionarcomentario(@PathVariable Integer id, @RequestBody MangaComentarioEntity comentario) {
        try {
            Optional<MangaEntity> comentarioManga = mangaRepository.findById(id);
            if (comentarioManga.isPresent()) {
                MangaEntity manga = comentarioManga.get();
                comentario.setManga(manga);
                comentario.setId(null);
                comentario.setDataCriacao(LocalDateTime.now());
                mangaRepository.save(manga);
                comentarioRepository.save(comentario);
                return ResponseEntity.status(HttpStatus.CREATED).body(comentario);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Manga com ID " + id + " não encontrado.");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao adicionar comentário: " + e.getMessage());
        }
    }

    @DeleteMapping("/manga/{mangaId}/comentarios/{comentarioId}")
    public ResponseEntity<?> deletarcomentario(@PathVariable Integer mangaId, @PathVariable Long comentarioId) {
        Optional<MangaEntity> manga = mangaRepository.findById(mangaId);
        if (manga.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Optional<MangaComentarioEntity> comentario = comentarioRepository.findById(comentarioId);
        if (comentario.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        mangaService.excluirComentario(comentario.get());
        return ResponseEntity.ok().build();
    }


}

