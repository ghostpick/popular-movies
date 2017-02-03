package com.popularmovies.ghostpick.popularmovies.data;

import android.content.Context;

public class PopularMoviesPreferences {

    private static final String DEFAULT_FILTER= "now_playing";

    public static String getPreferredWeatherLocation(Context context) {
        return DEFAULT_FILTER;
    }
}