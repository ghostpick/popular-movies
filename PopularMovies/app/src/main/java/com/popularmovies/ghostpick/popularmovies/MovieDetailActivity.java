package com.popularmovies.ghostpick.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.popularmovies.ghostpick.popularmovies.data.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Movie movie = (Movie) getIntent().getSerializableExtra("item_movie");

        TextView tv_movieDetails = (TextView) findViewById(R.id.tv_movieTitle);
        tv_movieDetails.setText(movie.getTitle());

        TextView tv_movieReleaseDate = (TextView) findViewById(R.id.tv_movieReleaseDate);
        tv_movieReleaseDate.setText(Html.fromHtml("<b>Release date</b> " +movie.getReleaseDate()));

        ImageView tv_moviePhoto = (ImageView) findViewById(R.id.tv_moviePhoto);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w500/" + movie.getPhoto()).into(tv_moviePhoto);

        TextView tv_movieVoteAverage = (TextView) findViewById(R.id.tv_movieVoteAverage);
        tv_movieVoteAverage.setText(Html.fromHtml("<b>Vote average</b> " + movie.getVoteAverage() +"/10"));

        TextView tv_smovieOverview = (TextView) findViewById(R.id.tv_smovieOverview);
        tv_smovieOverview.setText("Plot synopsis");

        TextView tv_movieOverview = (TextView) findViewById(R.id.tv_movieOverview);
        tv_movieOverview.setText(Html.fromHtml(movie.getOverview()));
    }
}


