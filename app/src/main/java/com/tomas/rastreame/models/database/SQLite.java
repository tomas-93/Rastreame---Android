package com.tomas.rastreame.models.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * Created by Tomas on 22/10/2015.
 */
public class SQLite extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String NAME_DATABASE = "Rastreame.db";

    public SQLite(Context context)
    {
        super(context, NAME_DATABASE, null, VERSION);
    }

    @Override public void onCreate(SQLiteDatabase database)
    {
        database.execSQL(Schema.CREATE_TABLE_CONFIG);
        database.execSQL(Schema.CREATE_TABLE_MESSAGE);

    }

    @Override public void onUpgrade(SQLiteDatabase database, int newVersion, int oldVersion)
    {
        database.execSQL(Schema.DROP_TABLE_CONFIG);
        database.execSQL(Schema.DROP_TABLE_MESSAGE);
        this.onCreate(database);
    }
}
