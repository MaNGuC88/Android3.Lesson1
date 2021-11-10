package com.example.lesson1.ui.film_detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.lesson1.App;
import com.example.lesson1.R;
import com.example.lesson1.data.models.Film;
import com.example.lesson1.databinding.FragmentFilmDetailBinding;
import com.example.lesson1.databinding.FragmentFilmsBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FilmDetailsFragment extends Fragment {

    public FragmentFilmDetailBinding binding;
    private Film film;

    public FilmDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFilmDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        film = (Film) requireArguments().getSerializable("film");

        App.api.getFilm(film.getId()).enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                if (response.isSuccessful() && response.body() != null) {
                    binding.TitleTv.setText("Title: " + film.getTitle());
                    binding.OriginalTitleTv.setText("Original title: " + film.getOriginalTitle());
                    binding.DirectorTv.setText("Director: " + film.getDirector());
                    binding.ProducerTv.setText("Producer: " + film.getProducer());
                    binding.ReleaseDateTv.setText("Release date: " + film.getReleaseDate());
                    binding.RunningTimeTv.setText("Running time: " + film.getRunningTime());
                    binding.DescriptionTv.setText("Description: " + film.getDescription());
                    Glide.with(binding.movieImage.getContext())
                            .load(film.getImageUrl())
//                            .circleCrop()
                            .into(binding.movieImage);
                } else {
                    Log.e("TAG", "onFailure" + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                Log.e("TAG", "onFailure" + t.getLocalizedMessage());
            }
        });
    }
}