package com.example.admin.sleepbetter;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class UserDiary {
 //   @NonNull
  //  @PrimaryKey
  //  private String date;

  //  private String username;

    @NonNull
    @PrimaryKey
    private String username;

    private String date;

    private String comment;

    public UserDiary() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}