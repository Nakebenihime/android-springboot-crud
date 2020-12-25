package org.androidspringbootfrontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.androidspringbootfrontend.adapter.CustomAdapter;
import org.androidspringbootfrontend.api.MangaService;
import org.androidspringbootfrontend.api.Retrofit2Client;
import org.androidspringbootfrontend.model.Manga;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    final MangaService service = Retrofit2Client.createService(MangaService.class);
    CustomAdapter customAdapter;
    ListView view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        view = (ListView) findViewById((R.id.view_mangas));
        view.setOnItemClickListener((parent, view, position, id) -> {
            final Manga manga = (Manga) parent.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);
            intent.putExtra("id", manga.getId());
            startActivity(intent);
        });
        this.findAll();
    }

    public void findAll() {
        Call<List<Manga>> call = service.findAll();
        call.enqueue(new Callback<List<Manga>>() {
            @Override
            public void onResponse(Call<List<Manga>> call, Response<List<Manga>> response) {
                if (response.isSuccessful()) {
                    List<Manga> mangas = response.body();
                    assert mangas != null;
                    customAdapter = new CustomAdapter(getApplicationContext(), mangas);
                    view.setAdapter(customAdapter);
                } else {
                    System.out.println(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<Manga>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.findAll();
    }
}
