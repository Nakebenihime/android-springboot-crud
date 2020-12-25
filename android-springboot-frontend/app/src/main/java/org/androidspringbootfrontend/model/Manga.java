package org.androidspringbootfrontend.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Manga {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("year")
    @Expose
    private int year;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("url")
    @Expose
    private String URL;


    public Manga(long id, String title, String author, int year, boolean status, String URL) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.status = status;
        this.URL = URL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}

