package com.asadmshah.moviegur.models.tmdb;

import com.asadmshah.moviegur.utils.TestResourceLoader;
import com.bluelinelabs.logansquare.LoganSquare;

import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MovieResponseUnitTest {

    @Test
    public void parseJsonResponse_movie() throws Exception {
        InputStream inputStream = TestResourceLoader.load(this, "tmdb_movie.json");
        MovieResponse response = LoganSquare.parse(inputStream, MovieResponse.class);

        assertNotNull(response);
        assertEquals(939841200000L, response.releaseDate.getTime());
        assertEquals(28, response.credits.cast.size());
        assertEquals(17, response.credits.crew.size());
        assertEquals(1, response.genres.size());
        assertEquals(25, response.images.backdrops.size());
        assertEquals(55, response.images.posters.size());
        assertEquals(2, response.reviews.reviews.size());
    }

}
