package com.popularmovies.ghostpick.popularmovies.data;

import android.content.Context;
import com.popularmovies.ghostpick.popularmovies.R;

public class PopularMoviesPreferences {

    public static String getPreferredWeatherLocation(Context context) {
        return context.getString(R.string.defaultFilter);
    }
}