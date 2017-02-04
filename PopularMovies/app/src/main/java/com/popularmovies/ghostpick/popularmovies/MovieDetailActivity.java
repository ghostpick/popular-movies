package com.popularmovies.ghostpick.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        String s_movie;
        TextView tv_movieDetails;

        tv_movieDetails = (TextView) findViewById(R.id.tv_display_movie);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                s_movie = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                tv_movieDetails.setText(s_movie);
            }
        }
    }
}


