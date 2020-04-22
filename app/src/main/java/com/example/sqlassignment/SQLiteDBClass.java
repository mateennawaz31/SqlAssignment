package com.example.sqlassignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SQLiteDBClass extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME="DBClass.db";
    private static final int DATABASE_VERSION=1;

    private Context context;
    private String queryToCreateTable="create table classtable (Name Varchar(255),Address VARCHAR(255))";
    public SQLiteDBClass(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try
        {
            db.execSQL(queryToCreateTable);
            Toast.makeText(context,"Database and Table created", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(context,"onCreateDB:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
