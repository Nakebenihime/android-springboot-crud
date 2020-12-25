package org.androidspringbootfrontend.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import org.androidspringbootfrontend.R;
import org.androidspringbootfrontend.model.Manga;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Manga> {

    private final Context context;
    private final List<Manga> mangas;


    public CustomAdapter(@NonNull Context context, @NonNull List<Manga> mangas) {
        super(context, 0, mangas);
        this.context = context;
        this.mangas = mangas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.row, parent, false);
        Manga manga = mangas.get(position);

        TextView name = rowView.findViewById(R.id.row_view);
        name.setText(manga.getTitle());

        ImageView imageView = rowView.findViewById(R.id.mini);
        Picasso.get().load(manga.getURL()).centerCrop().fit().into(imageView);

        return rowView;
    }
}

