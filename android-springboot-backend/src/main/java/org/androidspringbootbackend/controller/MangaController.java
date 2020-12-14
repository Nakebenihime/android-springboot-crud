package org.androidspringbootbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.androidspringbootbackend.error.ApiError;
import org.androidspringbootbackend.model.Manga;
import org.androidspringbootbackend.service.MangaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
@Tag(name = "mangas", description = "Manga API operations")
@RequestMapping("${api.uri.paths.mangas}")
@RestController
public class MangaController {

    private final MangaService mangaService;

    @Autowired
    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    @Operation(summary = "create a new manga", description = "create a new manga", tags = "mangas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The request to create a resource was successful and the response details were returned. The location header contains the URI of the created resource.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Manga.class))),
            @ApiResponse(responseCode = "400", description = "The request is not valid and cannot be accepted.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "415", description = "The request cannot be accepted because the media type of the request entity is not a format that the API understands. Make sure the Content-Type header of the request is application/json.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "The request failed. An error occurred on the server side.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class)))})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Manga> save(@Valid @RequestBody Manga manga, UriComponentsBuilder uriComponentsBuilder) {
        this.mangaService.save(manga);
        UriComponents uriComponents = uriComponentsBuilder.path("/api/v1/mangas/{id}").buildAndExpand(manga.getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .location(uriComponents.toUri())
                .body(manga);
    }

    @Operation(summary = "retrieve all mangas", description = "retrieve all mangas", tags = "mangas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The application was accepted and the response details were returned.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Manga.class))),
            @ApiResponse(responseCode = "500", description = "The request failed. An Error occurred on the server side.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Manga>> getAllMangas() {
        return ResponseEntity.ok(this.mangaService.findAll());
    }

    @Operation(summary = "update a manga", description = "update a manga", tags = "mangas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The application was accepted and the response details were returned.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Manga.class))),
            @ApiResponse(responseCode = "404", description = "The request cannot be fulfilled because the resource does not exist.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "415", description = "The request cannot be accepted because the media type of the request entity is not a format that the API understands. Make sure the Content-Type header of the request is application/json.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "The request failed. An Error occurred on the server side.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class)))})
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Manga> update(@RequestBody Manga manga) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.mangaService.update(manga));
    }

    @Operation(summary = "delete a manga by id", description = "delete a manga by id", tags = "mangas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The application was accepted and the response details were returned.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Manga.class))),
            @ApiResponse(responseCode = "404", description = "The request cannot be fulfilled because the resource does not exist.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "The request failed. An Error occurred on the server side.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class)))})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteMangaById(@Parameter(description = "Manga identification number") @PathVariable("id") long id) {
        this.mangaService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @Operation(summary = "retrieve a manga by id", description = "retrieve a manga by id", tags = "mangas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The application was accepted and the response details were returned.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Manga.class))),
            @ApiResponse(responseCode = "404", description = "The request cannot be fulfilled because the resource does not exist.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "The request failed. An Error occurred on the server side.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Manga>> getMangaById(@Parameter(description = "Manga identification number") @PathVariable("id") long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.mangaService.findById(id));
    }
}
