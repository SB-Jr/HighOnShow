package com.project.sbjr.showinfodatabase;


import android.view.View;

import com.project.sbjr.showinfodatabase.fetch.MoviesFetch;
import com.project.sbjr.showinfodatabase.fetch.TvShowFetch;
import com.project.sbjr.showinfodatabase.handler.MovieHandler;
import com.project.sbjr.showinfodatabase.model.MovieModel;
import com.project.sbjr.showinfodatabase.response.CreditResponse;
import com.project.sbjr.showinfodatabase.response.MovieResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sbjr on 19/12/16.
 *
 * Access class to all the Movie and tv show apis
 */

public class HighOnShow {

    private static final String BASE_URL="https://api.themoviedb.org/3/";

    private String apiKey;
    private Retrofit retrofit;
    
    public Movie Movie;

    public HighOnShow(String key) {
        //initApiKey();
        apiKey = key;
        buildClient();
    }

    private void buildClient(){
        if(apiKey!=null||!apiKey.isEmpty()){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }


    public Movie initMovie(){
        Movie = new Movie(this);
        return Movie;
    }

    public class Movie{

        private HighOnShow highOnShow;

        private MoviesFetch moviesFetch;

        public Movie(HighOnShow highOnShow) {

            this.highOnShow = highOnShow;

            if (retrofit == null) {
                buildClient();
                moviesFetch = retrofit.create(MoviesFetch.class);
            } else if (moviesFetch==null) {
                moviesFetch = retrofit.create(MoviesFetch.class);
            }
        }

        /**
         * get the upcoming movie list
         * */
        public void getUpcomingMovies(final View onCompleteShow, final View onDataFetchShow, final View onFailureShow, MovieHandler<MovieResponse> handler){

            onCompleteShow.setVisibility(View.GONE);
            onFailureShow.setVisibility(View.GONE);
            onDataFetchShow.setVisibility(View.VISIBLE);

            if(highOnShow.apiKey==null||highOnShow.apiKey.length()==0){
                onFailureShow.setVisibility(View.VISIBLE);
                onDataFetchShow.setVisibility(View.GONE);
                return;
            }
            handler.initViews(onCompleteShow,onDataFetchShow,onFailureShow);
            handler.startFetch(moviesFetch.getUpcomingMovies(apiKey, 1));
        }

        /**
         * get the movie detail
         * */
        public void getMovieDetails(int movieId,final View onCompleteShow, final View onDataFetchShow, final View onFailureShow,MovieHandler<MovieModel> handler){
            onCompleteShow.setVisibility(View.GONE);
            onFailureShow.setVisibility(View.GONE);
            onDataFetchShow.setVisibility(View.VISIBLE);

            if(highOnShow.apiKey==null||highOnShow.apiKey.length()==0){
                onFailureShow.setVisibility(View.VISIBLE);
                onDataFetchShow.setVisibility(View.GONE);
                return;
            }
            handler.initViews(onCompleteShow,onDataFetchShow,onFailureShow);
            handler.startFetch(moviesFetch.getMovieDetailsById(apiKey, movieId));
        }

        public void getMovieCredits(int movieId,final View onCompleteShow, final View onDataFetchShow, final View onFailureShow,MovieHandler<CreditResponse> handler){

            onCompleteShow.setVisibility(View.GONE);
            onFailureShow.setVisibility(View.GONE);
            onDataFetchShow.setVisibility(View.VISIBLE);

            if(highOnShow.apiKey==null||highOnShow.apiKey.length()==0){
                onFailureShow.setVisibility(View.VISIBLE);
                onDataFetchShow.setVisibility(View.GONE);
                return;
            }
            handler.initViews(onCompleteShow,onDataFetchShow,onFailureShow);
            handler.startFetch(moviesFetch.getMovieCreditsById(apiKey, movieId));
        }

        /**
         * get the movies matching a search keyword
         * */
        public ArrayList<MovieModel> getMovieBySearch(String query,final View onCompleteShow, final View onDataFetchShow, final View onFailureShow){
            onCompleteShow.setVisibility(View.GONE);
            onFailureShow.setVisibility(View.GONE);
            onDataFetchShow.setVisibility(View.VISIBLE);

            if(apiKey==null||apiKey.length()==0){
                onFailureShow.setVisibility(View.VISIBLE);
                onDataFetchShow.setVisibility(View.GONE);
                return new ArrayList<MovieModel>();
            }
            else {
                Call<MovieResponse> call = moviesFetch.getMoviesBySearch(apiKey, query);
                final MovieResponse mr = new MovieResponse();
                call.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        onCompleteShow.setVisibility(View.VISIBLE);
                        onDataFetchShow.setVisibility(View.GONE);
                        mr.setResults(response.body().getResults());
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        onFailureShow.setVisibility(View.VISIBLE);
                        onDataFetchShow.setVisibility(View.GONE);
                        mr.setResults(null);
                    }
                });
                return mr.getResults();
            }
        }

    }

    private class TvShow{

        private TvShowFetch tvShowFetch;



    }

}
