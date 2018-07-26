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

    @Insert
    void insertSingleUserDiary(UserDiary data);


    @Query("SELECT * FROM UserQuestionnaire WHERE username = :username")
    UserQuestionnaire fetchUseQuestionnaireByUsrname(String username);

    @Query("SELECT * FROM UserQuestionnaire")
    List<UserQuestionnaire> fetchUserQuestionnaires();


    @Query("SELECT * FROM UserExperiment")
    List<UserExperiment> fetchUserExperiments();

    @Query("SELECT mood_all FROM UserQuestionnaire")
    List<Integer> fetchMoods();

    @Query("SELECT timesPerNight FROM UserQuestionnaire")
    List<Integer> fetchTimsPerNight();

    @Query("SELECT nightTerrors FROM UserQuestionnaire")
    List<Integer> fetchNightTerrors();

    @Query("SELECT fallAsleep FROM UserQuestionnaire")
    List<Integer> fetchFallAsleep();

    @Query("SELECT wakeUp FROM UserQuestionnaire")
    List<Integer> fetchWakeUp();

    @Query("SELECT fresh FROM UserQuestionnaire")
    List<Integer> fetchFresh();

    @Query("SELECT sad FROM UserQuestionnaire")
    List<Integer> fetchSad();

    @Query("SELECT sleepy FROM UserQuestionnaire")
    List<Integer> fetchSleepy();

    @Query("SELECT tired FROM UserQuestionnaire")
    List<Integer> fetchTired();

    @Query("SELECT stressed FROM UserQuestionnaire")
    List<Integer> fetchStressed();

    @Query("SELECT apetite FROM UserQuestionnaire")
    List<Integer> fetchApetite();

    @Query("SELECT concentrate FROM UserQuestionnaire")
    List<Integer> ftchConcentrate();

    @Query("SELECT coordinate FROM UserQuestionnaire")
    List<Integer> fetchCoordinate();

    @Query("SELECT irritable FROM UserQuestionnaire")
    List<Integer> fetchIrritable();

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