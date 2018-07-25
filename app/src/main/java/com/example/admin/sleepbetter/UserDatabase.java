package com.example.admin.sleepbetter;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {UserQuestionnaire.class, UserExperiment.class}, version = 11, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess() ;
}