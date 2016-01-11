package com.asadmshah.moviegur.models.tmdb;

import com.asadmshah.moviegur.utils.TestResourceLoader;
import com.bluelinelabs.logansquare.LoganSquare;

import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MoviesListResponseUnitTest {

    @Test
    public void parseResponse_correctPage() throws Exception {
        MoviesListResponse response = getMoviesListResponse("tmdb_now_playing_1.json");
        assertNotNull(response);
        assertEquals(1, response.page);
        assertEquals(2, response.totalPages);
        assertEquals(40, response.totalResults);
        assertEquals(20, response.movies.size());
    }

    @Test
    public void parseResponse_incorrectPage() throws Exception {
        MoviesListResponse response = getMoviesListResponse("tmdb_now_playing_3.json");
        assertNotNull(response);
        assertEquals(3, response.page);
        assertEquals(2, response.totalPages);
        assertEquals(40, response.totalResults);
        assertEquals(0, response.movies.size());
    }

    private MoviesListResponse getMoviesListResponse(String filename) throws Exception {
        InputStream inputStream = TestResourceLoader.load(this, filename);
        return LoganSquare.parse(inputStream, MoviesListResponse.class);
    }

}
