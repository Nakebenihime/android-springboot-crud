package org.androidspringbootbackend.service;

import java.util.List;
import java.util.Optional;

public interface IMangaService<Manga> {

    Manga save(Manga t);

    List<Manga> findAll();

    Manga update(Manga manga);

    void deleteById(long id);

    Optional<Manga> findById(long id);
}
