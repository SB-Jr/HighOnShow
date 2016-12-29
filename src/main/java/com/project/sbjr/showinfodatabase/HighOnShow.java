package com.project.sbjr.showinfodatabase;


import android.view.View;

import com.project.sbjr.showinfodatabase.fetch.MoviesFetch;
import com.project.sbjr.showinfodatabase.fetch.TvShowFetch;
import com.project.sbjr.showinfodatabase.handler.ShowHandler;
import com.project.sbjr.showinfodatabase.model.MovieModel;
import com.project.sbjr.showinfodatabase.response.CreditResponse;
import com.project.sbjr.showinfodatabase.response.MovieResponse;
import com.project.sbjr.showinfodatabase.response.TvOnAirResponse;

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
    public TvShow TvShow;

    public HighOnShow(String key) {
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

    public TvShow initTvShow(){
        TvShow = new TvShow(this);
        return TvShow;
    }

    public class Movie{

        private HighOnShow highOnShow;

        private MoviesFetch moviesFetch;

        public Movie(HighOnShow highOnShow) {

            this.highOnShow = highOnShow;

            if (retrofit == null) {
                highOnShow.buildClient();
                moviesFetch = retrofit.create(MoviesFetch.class);
            } else if (moviesFetch==null) {
                moviesFetch = retrofit.create(MoviesFetch.class);
            }
        }

        /**
         * get the upcoming movie list
         * */
        public void getUpcomingMovies(final View onCompleteShow, final View onDataFetchShow, final View onFailureShow, ShowHandler<MovieResponse> handler){

            if(onCompleteShow!=null) {
                onCompleteShow.setVisibility(View.GONE);
            }
            if(onFailureShow!=null) {
                onFailureShow.setVisibility(View.GONE);
            }
            if(onDataFetchShow!=null) {
                onDataFetchShow.setVisibility(View.VISIBLE);
            }

            if(highOnShow.apiKey==null||highOnShow.apiKey.length()==0){
                if(onFailureShow!=null) {
                    onFailureShow.setVisibility(View.VISIBLE);
                }
                if(onDataFetchShow!=null) {
                    onDataFetchShow.setVisibility(View.GONE);
                }
                return;
            }
            handler.initViews(onCompleteShow,onDataFetchShow,onFailureShow);
            handler.startFetch(moviesFetch.getUpcomingMovies(apiKey, 1));
        }

        /**
         * get the movie detail
         * */
        public void getMovieDetails(int movieId,final View onCompleteShow, final View onDataFetchShow, final View onFailureShow,ShowHandler<MovieModel> handler){
            if(onCompleteShow!=null) {
                onCompleteShow.setVisibility(View.GONE);
            }
            if(onFailureShow!=null) {
                onFailureShow.setVisibility(View.GONE);
            }
            if(onDataFetchShow!=null) {
                onDataFetchShow.setVisibility(View.VISIBLE);
            }
            if(highOnShow.apiKey==null||highOnShow.apiKey.length()==0){
                if(onFailureShow!=null) {
                    onFailureShow.setVisibility(View.VISIBLE);
                }
                if(onDataFetchShow!=null) {
                    onDataFetchShow.setVisibility(View.GONE);
                }
                return;
            }
            handler.initViews(onCompleteShow,onDataFetchShow,onFailureShow);
            handler.startFetch(moviesFetch.getMovieDetailsById(apiKey, movieId));
        }

        public void getMovieCredits(int movieId,final View onCompleteShow, final View onDataFetchShow, final View onFailureShow,ShowHandler<CreditResponse> handler){

            if(onCompleteShow!=null) {
                onCompleteShow.setVisibility(View.GONE);
            }
            if(onFailureShow!=null) {
                onFailureShow.setVisibility(View.GONE);
            }
            if(onDataFetchShow!=null) {
                onDataFetchShow.setVisibility(View.VISIBLE);
            }

            if(highOnShow.apiKey==null||highOnShow.apiKey.length()==0){
                if(onFailureShow!=null) {
                    onFailureShow.setVisibility(View.VISIBLE);
                }
                if(onDataFetchShow!=null) {
                    onDataFetchShow.setVisibility(View.GONE);
                }
                return;
            }
            handler.initViews(onCompleteShow,onDataFetchShow,onFailureShow);
            handler.startFetch(moviesFetch.getMovieCreditsById(movieId,highOnShow.apiKey));
        }

        /**
         * get the movies matching a search keyword
         * */
        public ArrayList<MovieModel> getMovieBySearch(String query,final View onCompleteShow, final View onDataFetchShow, final View onFailureShow){
            if(onCompleteShow!=null) {
                onCompleteShow.setVisibility(View.GONE);
            }
            if(onFailureShow!=null) {
                onFailureShow.setVisibility(View.GONE);
            }
            if(onDataFetchShow!=null) {
                onDataFetchShow.setVisibility(View.VISIBLE);
            }

            if(apiKey==null||apiKey.length()==0){
                if(onFailureShow!=null) {
                    onFailureShow.setVisibility(View.VISIBLE);
                }
                if(onDataFetchShow!=null) {
                    onDataFetchShow.setVisibility(View.GONE);
                }
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

    public class TvShow{

        private TvShowFetch tvShowFetch;


        private HighOnShow highOnShow;

        public TvShow(HighOnShow highOnShow) {

            this.highOnShow = highOnShow;

            if (retrofit == null) {
                highOnShow.buildClient();
                tvShowFetch = highOnShow.retrofit.create(TvShowFetch.class);
            } else if (tvShowFetch==null) {
                tvShowFetch = highOnShow.retrofit.create(TvShowFetch.class);
            }
        }

        //todo:complete these methods
        public void getTvShowOnAir(final View onCompleteShow, final View onDataFetchShow, final View onFailureShow, ShowHandler<TvOnAirResponse> handler){
            if(onCompleteShow!=null) {
                onCompleteShow.setVisibility(View.GONE);
            }
            if(onFailureShow!=null) {
                onFailureShow.setVisibility(View.GONE);
            }
            if(onDataFetchShow!=null) {
                onDataFetchShow.setVisibility(View.VISIBLE);
            }

            if(highOnShow.apiKey==null||highOnShow.apiKey.length()==0){
                if(onFailureShow!=null) {
                    onFailureShow.setVisibility(View.VISIBLE);
                }
                if(onDataFetchShow!=null) {
                    onDataFetchShow.setVisibility(View.GONE);
                }
                return;
            }
            handler.initViews(onCompleteShow,onDataFetchShow,onFailureShow);
            handler.startFetch(tvShowFetch.getTvShowOnAir(highOnShow.apiKey,1));
        }

        public void getTvShowDetailsBySearch(){

        }

        public void getTvShowDetailsById(){

        }

        public void getTvShowSeasonsById(){

        }

    }

}
