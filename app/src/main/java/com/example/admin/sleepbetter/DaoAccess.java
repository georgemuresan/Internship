package com.example.admin.sleepbetter;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    void insertOnlySingleMovie(UserData data);
    @Insert
    void insertMultipleMovies (List<UserData> moviesList);
    @Query("SELECT * FROM UserData WHERE username = :username")
    UserData fetchOneMoviesbyMovieId(String username);
    @Query("SELECT * FROM UserData")
    List<UserData> fetchMovies();
    @Update
    void updateMovie(UserData data);
    @Delete
    void deleteMovie(UserData data);
}