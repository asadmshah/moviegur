package com.asadmshah.moviegur.screens.movie_summary;

public final class MovieSummaryScreenEvents {

    public static final class OnMovieSummaryScreenOpenAnimation {
        public final long duration;

        public OnMovieSummaryScreenOpenAnimation(long duration) {
            this.duration = duration;
        }
    }

    public static final class OnMovieSummaryScreenExitAnimation {
        public final long duration;

        public OnMovieSummaryScreenExitAnimation(long duration) {
            this.duration = duration;
        }
    }

}
