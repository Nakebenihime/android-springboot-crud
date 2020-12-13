package org.androidspringbootbackend.service;

import java.util.List;
import java.util.Optional;

public interface IMangaService<Manga> {

    Manga save(Manga t);
    Optional<Manga> findById(long id);
    void delete(Manga Manga);
    List<Manga> findAll();
    void deleteById(long id);
}
