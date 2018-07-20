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
    void insertSingleUserExperiment(UserExperiment data);


    @Query("SELECT * FROM UserQuestionnaire WHERE username = :username")
    UserQuestionnaire fetchUseQuestionnaireByUsrname(String username);

    @Query("SELECT * FROM UserQuestionnaire")
    List<UserQuestionnaire> fetchUserQuestionnaires();


    @Query("SELECT * FROM UserExperiment")
    List<UserExperiment> fetchUserExperiments();

    @Update
    void updateUSerQuestionnaire(UserQuestionnaire data);

    @Delete

    void deleteUserQuestonnaire(UserQuestionnaire data);

    @Query("DELETE FROM UserQuestionnaire")
    public void deleteUserQuesionnaireTable();
    @Query("DELETE FROM UserExperiment")
    public void deleteUserExperimentTable();
}