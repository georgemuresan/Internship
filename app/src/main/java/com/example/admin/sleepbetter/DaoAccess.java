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
    void insertSingleUserQuestionnaire(UserQuestionnaire data);
    @Insert
    void insertMultipleMovies (List<UserQuestionnaire> moviesList);
    @Query("SELECT * FROM UserQuestionnaire WHERE username = :username")
    UserQuestionnaire fetchOneMoviesbyMovieId(String username);
    @Query("SELECT * FROM UserQuestionnaire")
    List<UserQuestionnaire> fetchMovies();
    @Update
    void updateMovie(UserQuestionnaire data);
    @Delete
    void deleteMovie(UserQuestionnaire data);

    @Query("DELETE FROM UserQuestionnaire")
    public void deleteUserQuesionnaireTable();
    @Query("DELETE FROM UserExperiment")
    public void deleteUserExperimentTable();
}