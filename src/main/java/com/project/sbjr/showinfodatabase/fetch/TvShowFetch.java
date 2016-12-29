package com.project.sbjr.showinfodatabase.fetch;

import com.project.sbjr.showinfodatabase.model.TvShowModel;
import com.project.sbjr.showinfodatabase.response.TvOnAirResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sbjr on 19/12/16.
 */

public interface TvShowFetch {

    @GET("tv/on_the_air")
    Call<TvOnAirResponse> getTvShowOnAir(@Query("api_key") String apiKey,@Query("page") int page);

    @GET("tv/{id}")
    Call<TvShowModel> getTvShowById(@Query("api_key")String apiKey, @Path("id")int movieId);

}
