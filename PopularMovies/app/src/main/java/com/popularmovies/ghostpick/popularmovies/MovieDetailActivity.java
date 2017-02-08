package com.popularmovies.ghostpick.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import com.popularmovies.ghostpick.popularmovies.data.Movie;
import com.popularmovies.ghostpick.popularmovies.data.Vars;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        this.setTitle(this.getString(R.string.movieDetail_title));

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            //retrieving (using the same constant key) the object from the activity extras
            Movie movie = (Movie) bundle.getParcelable(Movie.PARCELABLE_KEY);

            TextView tv_movieDetails = (TextView) findViewById(R.id.tv_movieTitle);
            tv_movieDetails.setText(movie.getTitle());

            TextView tv_movieReleaseDate = (TextView) findViewById(R.id.tv_movieReleaseDate);
            tv_movieReleaseDate.setText(Html.fromHtml(Vars.releaseDate + movie.getReleaseDate()));

            ImageView tv_moviePhoto = (ImageView) findViewById(R.id.tv_moviePhoto);
            Picasso.with(this).load(Vars.IMAGE_LINK_500 + movie.getPhoto()).into(tv_moviePhoto);

            TextView tv_movieVoteAverage = (TextView) findViewById(R.id.tv_movieVoteAverage);
            tv_movieVoteAverage.setText(Html.fromHtml(Vars.VoteAverage + movie.getVoteAverage() + Vars.VoteAverageMax));

            TextView tv_smovieOverview = (TextView) findViewById(R.id.tv_smovieOverview);
            tv_smovieOverview.setText(Vars.synopsis);

            TextView tv_movieOverview = (TextView) findViewById(R.id.tv_movieOverview);
            tv_movieOverview.setText(Html.fromHtml(movie.getOverview()));
        }
    }
}


