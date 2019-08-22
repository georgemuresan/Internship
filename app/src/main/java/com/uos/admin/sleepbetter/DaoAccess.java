package com.uos.admin.sleepbetter;

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

    @Insert
    void insertSingleUserDiary(UserDiary data);


    @Query("SELECT * FROM UserQuestionnaire WHERE username = :username")
    UserQuestionnaire fetchUseQuestionnaireByUsrname(String username);

    @Query("SELECT * FROM UserQuestionnaire")
    List<UserQuestionnaire> fetchUserQuestionnaires();


    @Query("SELECT * FROM UserExperiment")
    List<UserExperiment> fetchUserExperiments();

    @Query("SELECT mood_all FROM UserQuestionnaire")
    List<Double> fetchMoods();

    @Query("SELECT awake FROM UserQuestionnaire")
    List<Integer> fetchAwake();

    @Query("SELECT earlier FROM UserQuestionnaire")
    List<Integer> fetchEarlier();

    @Query("SELECT howLong FROM UserQuestionnaire")
    List<Integer> fetchHowLong();

    @Query("SELECT nightsAWeek FROM UserQuestionnaire")
    List<Integer> fetchNightsAWeek();

    @Query("SELECT quality FROM UserQuestionnaire")
    List<Integer> fetchQuality();

    @Query("SELECT impactMood FROM UserQuestionnaire")
    List<Integer> fetchImpactMood();

    @Query("SELECT impactActivities FROM UserQuestionnaire")
    List<Integer> fetchImpactActivities();

    @Query("SELECT impactGeneral FROM UserQuestionnaire")
    List<Integer> fetchImpactGeneral();

    @Query("SELECT problem FROM UserQuestionnaire")
    List<Integer> fetchProblem();

    @Query("SELECT * FROM UserDiary")
    List<UserDiary> fetchDiary();

    @Update
    void updateUSerQuestionnaire(UserQuestionnaire data);

    @Delete

    void deleteUserQuestonnaire(UserQuestionnaire data);

    @Delete

    void deleteUserDairy(UserDiary data);

    @Query("DELETE FROM UserQuestionnaire")
    public void deleteUserQuesionnaireTable();
    @Query("DELETE FROM UserExperiment")
    public void deleteUserExperimentTable();
    @Query("DELETE FROM UserDiary")
    public void deleteUserDiaryTable();

    @Query("SELECT experiment FROM UserExperiment")
    public List<String> getExperiments();
}