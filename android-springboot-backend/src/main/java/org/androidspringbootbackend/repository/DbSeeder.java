package org.androidspringbootbackend.repository;

import lombok.extern.slf4j.Slf4j;
import org.androidspringbootbackend.model.Manga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class DbSeeder implements CommandLineRunner {

    private final MangaRepository mangaRepository;

    @Autowired
    public DbSeeder(MangaRepository mangaRepository) {
        this.mangaRepository = mangaRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        //DELETE ALL
        this.mangaRepository.deleteAll();
        log.info("mangas were deleted...");
        log.info("mangas");

        //CREATE
        List<Manga> mangas = Arrays.asList(
                Manga.builder().title("Dragon Ball Z").author("Akira Toriyama").year(1989).status(true).build(),
                Manga.builder().title("One Piece").author("Eiichiro Oda").year(1997).status(true).build(),
                Manga.builder().title("Fairy Tail").author("Hiro Mashima").year(1997).status(true).build()
        );
        log.info("mangas were created : {}", mangas);

        //SAVE
        mangaRepository.saveAll(mangas);
        log.info("INITIALIZED DATABASE");
    }
}
