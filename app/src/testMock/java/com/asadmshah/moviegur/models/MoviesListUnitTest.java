package com.asadmshah.moviegur.models;

import com.asadmshah.moviegur.models.tmdb.MoviesListResponse;
import com.asadmshah.moviegur.utils.ResourceLoader;
import com.bluelinelabs.logansquare.LoganSquare;

import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MoviesListUnitTest {

    @Test
    public void convertJsonModelToMoviesListModel() throws Exception {
        InputStream inputStream = ResourceLoader.load(this, "tmdb_upcoming_1.json");
        MoviesList moviesList = MoviesList.create(LoganSquare.parse(inputStream, MoviesListResponse.class));
        assertNotNull(moviesList);

        assertEquals(1, moviesList.page());
        assertEquals(2, moviesList.totalPages());

        // Removes any movies that don't have a responsible marketing team who failed to provide TMDB
        // with a backdrop, poster, and overview. And where the language is that of the English people.
        assertEquals(10, moviesList.movies().size());
    }

}
