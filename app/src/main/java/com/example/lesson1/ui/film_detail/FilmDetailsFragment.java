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
import com.example.lesson1.data.models.Film;
import com.example.lesson1.databinding.FragmentFilmDetailBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FilmDetailsFragment extends Fragment {

    public FragmentFilmDetailBinding binding;
    private Film film;

    public FilmDetailsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
            public void onResponse(@NonNull Call<Film> call, @NonNull Response<Film> response) {
                if (response.isSuccessful() && response.body() != null) {
                    binding.TitleTv.setText(String.format("Title: %s", film.getTitle()));
                    binding.OriginalTitleTv.setText(String.format("Original title: %s", film.getOriginalTitle()));
                    binding.DirectorTv.setText(String.format("Director: %s", film.getDirector()));
                    binding.ProducerTv.setText(String.format("Producer: %s", film.getProducer()));
                    binding.ReleaseDateTv.setText(String.format("Release date: %s", film.getReleaseDate()));
                    binding.RunningTimeTv.setText(String.format("Running time: %s", film.getRunningTime()));
                    binding.DescriptionTv.setText(String.format("Description: %s", film.getDescription()));
                    Glide.with(binding.movieImage.getContext())
                            .load(film.getImageUrl())
//                            .circleCrop()
                            .into(binding.movieImage);
                } else {
                    Log.e("TAG", "onFailure" + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Film> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure" + t.getLocalizedMessage());
            }
        });
    }
}