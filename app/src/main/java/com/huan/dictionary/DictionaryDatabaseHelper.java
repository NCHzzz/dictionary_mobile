package com.huan.dictionary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DictionaryDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "huan_dictionary.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "words";
    public static final String COLUMN_WORD = "word";
    public static final String COLUMN_DEFINITION = "definition";

    public DictionaryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_WORD + " TEXT PRIMARY KEY," +
                COLUMN_DEFINITION + " TEXT)";
        db.execSQL(SQL_CREATE_ENTRIES);

        // Insert some sample words
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES ('facetious', 'Treating serious issues with deliberately inappropriate humor')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES ('gregarious', 'Fond of company; sociable')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES ('hypothetical', 'Based on or serving as a hypothesis')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES ('juxtapose', 'Place or deal with close together for contrasting effect')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES ('lucid', 'Expressed clearly; easy to understand')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES ('nostalgia', 'A sentimental longing for the past')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
