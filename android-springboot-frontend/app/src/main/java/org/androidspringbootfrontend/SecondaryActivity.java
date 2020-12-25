package org.androidspringbootfrontend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import org.androidspringbootfrontend.api.MangaService;
import org.androidspringbootfrontend.api.Retrofit2Client;
import org.androidspringbootfrontend.model.Manga;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondaryActivity extends Activity {

    final MangaService service = Retrofit2Client.createService(MangaService.class);
    Manga manga;
    Button btnUpdate;
    Button btnDelete;
    EditText textTitle;
    EditText textAuthor;
    EditText textYear;
    EditText textStatus;
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_details);

        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
        textTitle = findViewById(R.id.title);
        textAuthor = findViewById(R.id.author);
        textYear = findViewById(R.id.year);
        imageView = findViewById(R.id.image_view_detail);

        final Intent intent = getIntent();
        final long id = intent.getLongExtra("id", 0);

        btnUpdate.setOnClickListener(v -> {
            updateById(id);
            finish();
        });

        btnDelete.setOnClickListener(v -> {
            deleteById(id);
            finish();
        });
        findById(id);
    }

    public void setFindById(Manga manga) {

        final String title = manga.getTitle();
        textTitle.setText(title);

        final String author = manga.getAuthor();
        textAuthor.setText(author);

        final String year = Integer.toString(manga.getYear());
        textYear.setText(year);

        final String URL = manga.getURL();
        Picasso.get().load(URL).centerCrop().fit().into(imageView);
    }

    public void findById(long id) {
        Call<Manga> call = service.findById(id);
        call.enqueue(new Callback<Manga>() {
            @Override
            public void onResponse(Call<Manga> call, Response<Manga> response) {
                if (response.isSuccessful()) {
                    manga = response.body();
                    assert manga != null;
                    setFindById(manga);
                    Toast.makeText(SecondaryActivity.this, "FindById operation succeed!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SecondaryActivity.this, "FindById operation failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Manga> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void deleteById(long id) {
        Call<Manga> call = service.delete(id);
        call.enqueue(new Callback<Manga>() {
            @Override
            public void onResponse(Call<Manga> call, Response<Manga> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SecondaryActivity.this, "DeleteById operation succeed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Manga> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updateById(long id) {
        Manga modified = new Manga(id, textTitle.getText().toString(), textAuthor.getText().toString(), Integer.parseInt(textYear.getText().toString()), Boolean.parseBoolean(textStatus.getText().toString()), manga.getURL());
        Call<Manga> call = service.update(modified);
        call.enqueue(new Callback<Manga>() {
            @Override
            public void onResponse(Call<Manga> call, Response<Manga> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SecondaryActivity.this, "UpdateById operation succeed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Manga> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
