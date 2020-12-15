package org.androidspringbootbackend.service;

import lombok.extern.slf4j.Slf4j;
import org.androidspringbootbackend.exception.NotFoundException;
import org.androidspringbootbackend.model.Manga;
import org.androidspringbootbackend.repository.MangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MangaService implements IMangaService<Manga> {

    private final MangaRepository mangaRepository;

    @Autowired
    public MangaService(MangaRepository mangaRepository) {
        this.mangaRepository = mangaRepository;
    }

    @Override
    public Manga save(Manga manga) {
        log.info("creating hotel:{}", manga);
        return this.mangaRepository.save(manga);
    }

    @Override
    public List<Manga> findAll() {
        if (this.mangaRepository.findAll().isEmpty()) {
            throw new NotFoundException("No mangas were found");
        }
        return this.mangaRepository.findAll();
    }

    @Override
    public Manga update(Manga manga) {
        log.info("Fetching manga with id {}", manga.getId());
        this.mangaRepository.findById(manga.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Manga with id (%s) could not be found", manga.getId())));
        log.info("Updating manga with id {}", manga.getId());
        return this.mangaRepository.save(manga);
    }

    @Override
    public void deleteById(long id) {
        log.info("Fetching manga with id {}", id);
        this.mangaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Manga with id (%s) could not be found", id)));
        this.mangaRepository.deleteById(id);
    }

    @Override
    public Optional<Manga> findById(long id) {
        log.info("Fetching manga with id {}", id);
        this.mangaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Manga with id (%s) could not be found", id)));
        return this.mangaRepository.findById(id);
    }
}
