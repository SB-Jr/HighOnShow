package com.project.sbjr.showinfodatabase;


import android.view.View;

import com.project.sbjr.showinfodatabase.fetch.MoviesFetch;
import com.project.sbjr.showinfodatabase.fetch.TvShowFetch;
import com.project.sbjr.showinfodatabase.handler.MovieHandler;
import com.project.sbjr.showinfodatabase.model.MovieModel;
import com.project.sbjr.showinfodatabase.response.MovieResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

    /*private void initApiKey() {
        File f = new File();
        if(f.exists()){
            try {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                apiKey = br.readLine();
                apiKey = apiKey.trim();
                br.close();
                fr.close();
                if(apiKey==null){
                    throw new RuntimeException(f.getAbsolutePath() + " file is empty!!");
                }
            } catch (IOException e) {
                apiKey=null;
                e.printStackTrace();
                System.out.println(e.toString());
                throw new RuntimeException(f.getAbsolutePath() + " file is empty!!");
            }
        }
        else{
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                throw new RuntimeException(f.getAbsolutePath() + " file is empty!!");
            }

        }

    }*/


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

            if(highOnShow.apiKey==null||apiKey.length()==0){
                onFailureShow.setVisibility(View.VISIBLE);
                onDataFetchShow.setVisibility(View.GONE);
            }
            handler.initViews(onCompleteShow,onDataFetchShow,onFailureShow);
            handler.startFetch(moviesFetch.getUpcomingMovies(apiKey, 1));

            /*
            else {
                Call<MovieResponse> call = moviesFetch.getUpcomingMovies(apiKey, 1);
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
            }*/
        }

        /**
         * get the movie detail
         * */
        public MovieModel getMovieDetails(int movieId,final View onCompleteShow, final View onDataFetchShow, final View onFailureShow){
            onCompleteShow.setVisibility(View.GONE);
            onFailureShow.setVisibility(View.GONE);
            onDataFetchShow.setVisibility(View.VISIBLE);

            if(apiKey==null||apiKey.length()==0){
                onFailureShow.setVisibility(View.VISIBLE);
                onDataFetchShow.setVisibility(View.GONE);
                return null;
            }
            else {
                Call<MovieModel> call = moviesFetch.getMovieDetailsById(apiKey, movieId);
                final MovieModel[] movieModel = new MovieModel[1];
                call.enqueue(new Callback<MovieModel>() {
                    @Override
                    public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                        onCompleteShow.setVisibility(View.VISIBLE);
                        onDataFetchShow.setVisibility(View.GONE);
                        movieModel[0] = response.body();
                    }

                    @Override
                    public void onFailure(Call<MovieModel> call, Throwable t) {
                        onFailureShow.setVisibility(View.VISIBLE);
                        onDataFetchShow.setVisibility(View.GONE);
                        movieModel[0] = null;
                    }
                });
                return movieModel[0];
            }
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
