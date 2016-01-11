package com.asadmshah.moviegur.models;

import com.asadmshah.moviegur.models.tmdb.MovieResponse;
import com.asadmshah.moviegur.utils.TestResourceLoader;
import com.bluelinelabs.logansquare.LoganSquare;

import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class MovieUnitTest {

    @Test
    public void convertJsonModelToMovieModel() throws Exception {
        InputStream inputStream = TestResourceLoader.load(this, "tmdb_movie.json");
        Movie movie = Movie.create(LoganSquare.parse(inputStream, MovieResponse.class));

        assertEquals(939841200000L, movie.releaseDate());
        assertEquals(28, movie.credits().castMembers().size());
        assertEquals(17, movie.credits().crewMembers().size());
        assertEquals(1, movie.genres().size());
        assertEquals(25, movie.images().backdrops().size());
        assertEquals(55, movie.images().posters().size());
        assertEquals(2, movie.reviews().size());
    }

}
