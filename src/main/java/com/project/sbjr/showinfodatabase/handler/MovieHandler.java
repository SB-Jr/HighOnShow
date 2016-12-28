package com.project.sbjr.showinfodatabase.handler;

import android.util.Log;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sbjr on 27/12/16.
 */
public abstract class MovieHandler<T> implements Callback<T>{


    Call<T> call;
    Response<T> response;

    View onFalireView,onDataFetchView,onSuccessView;

    abstract public void onResult(T result);

    abstract public void onFailure();

    public void startFetch(Call<T> call){
        this.call = call;
        onFalireView.setVisibility(View.GONE);
        onSuccessView.setVisibility(View.GONE);
        onDataFetchView.setVisibility(View.VISIBLE);
        call.enqueue(this);
    }

    public void initViews(View onCompleteShow, View onDataFetchShow,View onFailureShow){
        this.onFalireView = onFailureShow;
        onSuccessView = onCompleteShow;
        onDataFetchView = onDataFetchShow;
    }


    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        onFalireView.setVisibility(View.GONE);
        onSuccessView.setVisibility(View.VISIBLE);
        onDataFetchView.setVisibility(View.GONE);
        this.response = response;
        onResult(response.body());
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFalireView.setVisibility(View.VISIBLE);
        onSuccessView.setVisibility(View.GONE);
        onDataFetchView.setVisibility(View.GONE);
        this.response = response;
        onFailure();
        Log.d(MovieHandler.class.getName(),t.getMessage()+"---"+t.toString());
    }
}
