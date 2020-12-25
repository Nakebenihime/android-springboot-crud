package org.androidspringbootfrontend.api;

import org.androidspringbootfrontend.model.Manga;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MangaService {

    @GET("mangas")
    Call<List<Manga>> findAll();

    @GET("mangas/{id}")
    Call<Manga> findById(@Path("id") long id);

    @POST("mangas")
    Call<Manga> save(@Body Manga manga);

    @PUT("mangas/{id}")
    Call<Manga> update(@Body Manga manga);

    @DELETE("mangas/{id}")
    Call<Manga> delete(@Path("id") long id);


}
