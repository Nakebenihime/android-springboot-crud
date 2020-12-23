package org.androidspringbootbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "mangas")
public class Manga {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "year")
    private int year;
    @Column(name = "status")
    private boolean status;
    @Column(name = "url")
    private String URL;
}
