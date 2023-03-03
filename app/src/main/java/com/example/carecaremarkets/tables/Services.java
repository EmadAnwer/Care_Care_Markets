package com.example.carecaremarkets.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Blob;
@Entity(tableName = "Services")
public class Services {
    /*
    "service_id"	INTEGER NOT NULL,
	"SP_id"	INTEGER,
	"name_AR"	TEXT,
	"name_EN"	TEXT,
	"price"	INTEGER,
     */
    @PrimaryKey(autoGenerate = true)
    public int service_id;
    public int SP_id;
    public String name_AR;
    public String name_EN;
    public int price;
}
