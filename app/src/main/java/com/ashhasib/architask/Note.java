package com.ashhasib.architask;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;


    private String name;
    private String desc;
    private int priority;

    //public Note(){}

    public Note(String name, String desc, int priority) {
        this.name = name;
        this.desc = desc;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {this.id = id;}
    public int getId() {return id;}
    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}


