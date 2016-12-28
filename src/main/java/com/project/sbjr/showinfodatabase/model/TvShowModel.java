package com.project.sbjr.showinfodatabase.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sbjr on 27/12/16.
 */

public class TvShowModel implements Parcelable{


    private String poster_path;
    private float vote_average;
    private int id;
    private String backdrop_path;
    private String overview;
    private String name;
    private String last_air_date;
    private int number_of_seasons;

    public TvShowModel(String poster_path, int vote_average, int id, String backdrop_path, String overview, String name) {
        this.poster_path = poster_path;
        this.vote_average = vote_average;
        this.id = id;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.name = name;
    }

    public TvShowModel(String poster_path, int vote_average, int id, String backdrop_path, String overview, String name, String last_air_date, int number_of_seasons) {
        this.poster_path = poster_path;
        this.vote_average = vote_average;
        this.id = id;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.name = name;
        this.last_air_date = last_air_date;
        this.number_of_seasons = number_of_seasons;
    }


    protected TvShowModel(Parcel in) {
        poster_path = in.readString();
        vote_average = in.readFloat();
        id = in.readInt();
        backdrop_path = in.readString();
        overview = in.readString();
        name = in.readString();
        last_air_date = in.readString();
        number_of_seasons = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster_path);
        dest.writeFloat(vote_average);
        dest.writeInt(id);
        dest.writeString(backdrop_path);
        dest.writeString(overview);
        dest.writeString(name);
        dest.writeString(last_air_date);
        dest.writeInt(number_of_seasons);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TvShowModel> CREATOR = new Creator<TvShowModel>() {
        @Override
        public TvShowModel createFromParcel(Parcel in) {
            return new TvShowModel(in);
        }

        @Override
        public TvShowModel[] newArray(int size) {
            return new TvShowModel[size];
        }
    };

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }
}
